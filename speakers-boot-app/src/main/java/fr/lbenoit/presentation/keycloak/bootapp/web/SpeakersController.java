package fr.lbenoit.presentation.keycloak.bootapp.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class SpeakersController {

    private final ObjectMapper objectMapper;

    SpeakersController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/speakers")
    public String speakers(Model model) throws IOException {
    	InputStream is = getClass().getResourceAsStream("/speakers.json");
    	if (is == null) {
    		return "errFichierAbsent";
    	}
        List<Map<String, Object>> speakers =
                objectMapper.readValue(is,
                        new TypeReference<ArrayList<HashMap<String, Object>>>() {});

        model.addAttribute("speakers", speakers);
        return "speakers";
    }

    @GetMapping("/change-password")
    public String changePassword(RedirectAttributes attributes, HttpServletRequest request, HttpServletResponse response) {
        return "err501";
    }

}