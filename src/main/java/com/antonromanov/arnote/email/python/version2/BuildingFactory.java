package com.antonromanov.arnote.email.python.version2;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class BuildingFactory {

	private PyObject buildingClass;

	/**
	 * Create a new PythonInterpreter object, then use it to
	 * execute some python code. In this case, we want to
	 * import the python module that we will coerce.
	 *
	 * Once the module is imported than we obtain a reference to
	 * it and assign the reference to a Java variable
	 */

	public BuildingFactory() {
		PythonInterpreter interpreter = new PythonInterpreter();

		interpreter.exec("sys.path.append('C:\\CODING\\GIT\\arNote\\src\\main\\java\\com\\antonromanov\\arnote\\email\\python\\version2\\')");
		interpreter.exec("from Building import Building");
		buildingClass = interpreter.get("Building");
	}

	/**
	 * The create method is responsible for performing the actual
	 * coercion of the referenced python module into Java bytecode
	 */

	public BuildingType create (String name, String location, String id) {

		PyObject buildingObject = buildingClass.__call__(new PyString(name),
				new PyString(location),
				new PyString(id));
		return (BuildingType)buildingObject.__tojava__(BuildingType.class);
	}

}
