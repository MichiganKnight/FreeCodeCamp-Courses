package com.revature.projects.socialMediaBlogAPIV1;

import com.revature.projects.socialMediaBlogAPIV1.Controller.SocialMediaController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Social Media Blog API ===");

        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();

        app.start(8080);
    }
}
