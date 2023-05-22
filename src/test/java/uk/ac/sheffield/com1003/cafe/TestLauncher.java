package uk.ac.sheffield.com1003.cafe;

import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class TestLauncher {

    public static String TEST_DIRECTORY = "src/test/java/uk/ac/sheffield/com1003/cafe";
    public static String PACKAGE_NAME = "uk.ac.sheffield.com1003.cafe";
    public static SummaryGeneratingListener listener = new SummaryGeneratingListener();

    public void runOne() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(selectClass(TestCafe.class)).build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

    }

    public List<String> listTestClasses() {
        String currentClass = this.getClass().getSimpleName();
        return Stream.of(Objects.requireNonNull(new File(TEST_DIRECTORY).listFiles())).map(File::getName).filter(name -> !name.contains(currentClass)).collect(Collectors.toList());
    }

    public void listTestMethods() {
        List<String> classStrings = listTestClasses();
        for (String classString : classStrings) {
            try {
                Class<?> testClass = Class.forName(String.join(".", PACKAGE_NAME, classString.replace(".java", "")));
                for (Method testMethod : testClass.getDeclaredMethods()) {
                    if (testMethod.isAnnotationPresent(Test.class)) {
                        System.out.println(String.join(".", testClass.getName(), testMethod.toString()));
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        TestLauncher testLauncher = new TestLauncher();
        testLauncher.listTestMethods();
    }
}
