package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class SupervisorPreferenceRepresentation {
    public Integer id;
    public String startTime;
    public String endTime;
    public List<String> excludeDates = new ArrayList<>();
}
