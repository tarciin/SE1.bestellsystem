package system.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import system.Calculator;
import system.DatamodelFactory;
import system.Formatter;
import system.IoC;
import system.OrderBuilder;
import system.Printer;

/**
 * Implementation class of an "Inversion-of-Control" (IoC) container, which
 * creates and holds system component objects.
 * 
 * @version <code style=
 *          color:green>{@value application.package_info#Version}</code>
 * @author <code style=
 *         color:blue>{@value application.package_info#Author}</code>
 */

public final class IoC_ContainerImpl implements IoC {

	/**
	 * Private static singleton IoC_ContainerImpl instance.
	 */
	private static final IoC_ContainerImpl singleton = new IoC_ContainerImpl();

	/**
	 * References to singleton objects that implement system component interfaces.
	 */
	private final Calculator calculator;
	private final Formatter formatter;
	private final Printer printer;
	private final DatamodelFactory datamodelfactory;
	private final OrderBuilder orderbuilder;
	private final java.util.Properties props = new Properties();

	/**
	 * Private constructor to prevent instance creation outside this class.
	 */
	private IoC_ContainerImpl() {
		this.calculator = new CalculatorImpl();
		this.formatter = new FormatterImpl();
		this.printer = new PrinterImpl(calculator, formatter);
		this.datamodelfactory = new DatamodelFactoryImpl();
		this.orderbuilder = new OrderBuilderImpl(datamodelfactory);
	}

	/**
	 * Getter of singleton instance that implements the {@link IoC} interface.
	 * 
	 * @return reference to singleton IoC instance.
	 */
	public static IoC getInstance() {
		return singleton;
	}

	/**
	 * Getter of system singleton component that implements the {@link Calculator}
	 * interface.
	 * 
	 * @return reference to singleton Calculator instance.
	 */
	@Override
	public Calculator getCalculator() {
		return this.calculator;
	}

	/**
	 * Getter of system singleton component that implements the {@link Formatter}
	 * interface.
	 * 
	 * @return reference to singleton Formatter instance.
	 */
	@Override
	public Formatter getFormatter() {
		return this.formatter;
	}

	/**
	 * Getter of system singleton component that implements the {@link Printer}
	 * interface.
	 * 
	 * @return reference to singleton Printer instance.
	 */
	@Override
	public Printer getPrinter() {
		return this.printer;
	}

	@Override
	public DatamodelFactory getDatamodelFactory() {
		return this.datamodelfactory;
	}

	@Override
	public OrderBuilder getOrderBuilder() {
		return this.orderbuilder;
	}
	
	@Override
	public Properties getProperties() {
		return this.props;
	}
	
	@Override
	public int loadProperties(String propertyFilePath) {
		int count = props.size();
		
		try {
			props.load(new FileInputStream(propertyFilePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}
}

