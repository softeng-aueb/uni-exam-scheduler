package gr.aueb.app.resource;

import jakarta.ws.rs.FormParam;
import java.io.InputStream;

public class FormData {
    @FormParam("file")
    public InputStream file;
}