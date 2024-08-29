package org.example;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

public class Suites {
    @Suite
    @SelectPackages({
            "org.example.Application.DTOs",
            "org.example.Application.Handlers",
            "org.example.Application.Services",
            "org.example.Domain.Model",
            "org.example.Infrastructure.Adapter.Input.REST",
            "org.example.Infrastructure.Adapter.Output",
            "org.example.Infrastructure.Config.SpringBoot",
    })
    public static class AllTests {
    }
}
