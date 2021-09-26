package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoApplicationRunner implements ApplicationRunner, ExitCodeGenerator, ExitCodeExceptionMapper {
  private static final Logger logger = LoggerFactory.getLogger(DemoApplicationRunner.class);

  private int exitCode;

  @Override
  public void run(ApplicationArguments args) {
    if (args.containsOption("h") || args.containsOption("help")) {
      System.out.println();
      System.out.println("[Usage]");
      System.out.println("  java -jar spring-boot-cli-demo.jar {calculation expressions}");
      System.out.println();
      System.out.println("[Command named arguments]");
      System.out.println("  --h (--help)");
      System.out.println("       print help");
      System.out.println("  --v (--version)");
      System.out.println("       print version");
      System.out.println();
      System.out.println("[Exit Codes]");
      System.out.println("  0 : Normal");
      System.out.println("  1 : Application error");
      System.out.println("  2 : Command arguments invalid");
      System.out.println("  3 : Calculation error");
      return;
    } else if (args.containsOption("v") || args.containsOption("version")) {
      System.out.println();
      System.out.println("Version : " + getClass().getPackage().getImplementationVersion());
      return;
    }
    List<String> values = args.getNonOptionArgs();
    if (values.isEmpty()) {
      this.exitCode = 2;
      logger.warn("calculation expressions is required.");
      return;
    }
    String expressionString = String.join(" ", values);
    System.out.println("Expression : " + expressionString);
    System.out.println("Result     : " + new SpelExpressionParser().parseExpression(expressionString).getValue());
  }

  /**
   * Provides exit code for application.
   * <p>
   * If not present this implementation, when normal end always return 0, when abnormal end return exit code that determine exception handling.
   * </p>
   */
  @Override
  public int getExitCode() {
    return exitCode;
  }

  /**
   * Implements processing that map exception to exit code.
   * <p>
   * If not present this implementation, always exit with 1.
   * </p>
   */
  @Override
  public int getExitCode(Throwable exception) {
    return exception.getCause() != null && exception.getCause() instanceof SpelEvaluationException ? 3 : 1;
  }

}
