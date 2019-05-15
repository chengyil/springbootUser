package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {	
	private final UserRepository repository;
	
	UserController(UserRepository repository) {
	    this.repository = repository;
    }

	@RequestMapping("/")
    public String home() {
        return "index";
    }
   
	@GetMapping("/importusers")
    public String importUsers(Model model) {
        return "importusers";
    }
	
	@PostMapping("/handleimportusers")
    public String handleImportUsers(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		List<User> users = processData(file);
		redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded" + file.getOriginalFilename() + " with users added "+ users.size() +"!");
        return "redirect:/importusers";
    }
	
	private List<User> processData(MultipartFile file) {
		List<User> users = new ArrayList<User>();
		String userData = null;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			// Assume Header is always there.
			String header = bufferedReader.readLine();
			while ((userData = bufferedReader.readLine()) != null) {
				String[] data = userData.split(",");
				// TODO Validate Data
				User user = new User(data[0], Double.parseDouble(data[1]));
				repository.save(user);
				users.add(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	@GetMapping("/users")
	@ResponseBody
	public List<User> users(Model model) {
		return repository.validUserAndSalary();
    }
}