package test;

import java.io.File;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;

public class RunCalculatorTest {

	public static void main(String[] args) throws Exception {
		Context context = new Context();
		try {
			Interpreter.process(context,new File("./sample_scripts/calculator.po"));
		} catch (PoslException e) {
			System.out.println(e.toString());
		}
	}

}
