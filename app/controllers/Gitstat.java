package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.gitstat;

import com.fasterxml.jackson.databind.ObjectMapper;

import static play.data.Form.form;

public class Gitstat extends Controller {

    public static class Search {
        public static String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";

        public String fromDate;
        public String toDate;
        public String data;

        public String validate() {
            if (fromDate == null || fromDate.length() < 1) {
                return "Field [from date] cannot be empty!";
            }
            if (fromDate == null || fromDate.length() < 1) {
                return "Field [from date] cannot be empty!";
            }
            return null;
        }
    }

    public static Result search() {
        Form<Search> searchForm = form(Search.class).bindFromRequest();
        if (searchForm.hasErrors()) {
            return badRequest(gitstat.render(searchForm, null));
        }
        Search search = searchForm.get();
        String found = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
            switch (search.data) {
            case "commits":
                found = new ObjectMapper().writeValueAsString(CommitTracker.getInstance().getBackCommits(sdf.parse(search.fromDate), sdf.parse(search.toDate)));
                break;
            case "issues":
                found = new ObjectMapper().writeValueAsString(IssueTracker.getInstance().getClosedIssues(sdf.parse(search.fromDate)));
                break;
            default:
                break;
            }
        } catch (IOException | ParseException e) {
            flash("error", e.getMessage());
            return redirect(routes.Gitstat.index());
        }
        return ok(gitstat.render(form(Search.class), found));
    }

    public static Result index() {
        return ok(gitstat.render(form(Search.class), null));
    }

}
