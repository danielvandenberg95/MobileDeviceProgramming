/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package nl.dvandenberg.mobiledeviceprogramming.backend;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {
    int highestAge = 0;
    String oldestName = "Adam";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println(oldestName);
        resp.getWriter().println(highestAge);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        Integer age = 0;
        try {
            age = Integer.parseInt(req.getParameter("age"));
        } catch (NumberFormatException ignored) {
            resp.getWriter().println("Please enter an age");
            return;
        }
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }

        if (age > highestAge) {
            highestAge = age;
            oldestName = name;
        }
        resp.getWriter().println(oldestName);
        resp.getWriter().println(highestAge);
    }
}
