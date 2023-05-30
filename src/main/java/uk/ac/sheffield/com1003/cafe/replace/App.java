package uk.ac.sheffield.com1003.cafe.replace;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.checkerframework.checker.units.qual.A;
import uk.ac.sheffield.com1003.cafe.causality.DataDependence;
import uk.ac.sheffield.com1003.cafe.causality.MethodCall;
import uk.ac.sheffield.com1003.cafe.causality.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class App {
    public static String DIR_SOLUTION;
    public static String DIR_REFERENCE;
    public static Set<File> solutionFiles;
    public static Set<File> referenceFiles;
    public static CompilationUnit cu;

    public App() {
        DIR_SOLUTION = Util.getOSPath();
        DIR_REFERENCE = Util.getReferencePath();
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver(DIR_SOLUTION));
        combinedTypeSolver.add(new JavaParserTypeSolver(DIR_REFERENCE));

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getParserConfiguration().setSymbolResolver(symbolSolver);

        solutionFiles = Util.getFiles(new File(DIR_SOLUTION));
        referenceFiles = Util.getFiles(new File(DIR_REFERENCE));
    }

    public void removeMethod(CompilationUnit cu, String methodName) {
        new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration method, Void arg) {
                if (method.resolve().getQualifiedName().equals(methodName)) {
                    System.out.println(method.getBody());
                }
            }
        }.visit(cu, null);
    }

    public void referenceMethodVisitor(CompilationUnit cu, String methodName) {
        new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration method, Void arg) {
                if (method.resolve().getQualifiedName().equals(methodName)) {
                    System.out.println(method.getBody());
                }
            }
        }.visit(cu, null);
    }

    public void getReferenceImplementation(String methodName) {
        for (File file : referenceFiles) {
            try {
                System.out.println(file);
                cu = StaticJavaParser.parse(file);
                referenceMethodVisitor(cu, methodName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        String confoundingMethod = "uk.ac.sheffield.com1003.cafe.Recipe.getPrice";

        App app = new App();
        app.getReferenceImplementation(confoundingMethod);
    }
}
