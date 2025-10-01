// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package datawarehouse_project.transformation_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: Transformation Purpose: nettoyage des donnees<br>
 * Description: nettoyage des donnees mal definis, gerer les doublons et les
 * valeusr null... <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class Transformation implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "Transformation";
	private final String projectName = "DATAWAREHOUSE_PROJECT";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					Transformation.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(Transformation.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_5_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_5_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_5_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_4_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_5_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class CT_categorieStruct implements routines.system.IPersistableRow<CT_categorieStruct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int id_categorie;

		public int getId_categorie() {
			return this.id_categorie;
		}

		public String nom_categorie;

		public String getNom_categorie() {
			return this.nom_categorie;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.id_categorie;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final CT_categorieStruct other = (CT_categorieStruct) obj;

			if (this.id_categorie != other.id_categorie)
				return false;

			return true;
		}

		public void copyDataTo(CT_categorieStruct other) {

			other.id_categorie = this.id_categorie;
			other.nom_categorie = this.nom_categorie;

		}

		public void copyKeysDataTo(CT_categorieStruct other) {

			other.id_categorie = this.id_categorie;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_categorie = dis.readInt();

					this.nom_categorie = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_categorie = dis.readInt();

					this.nom_categorie = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.id_categorie);

				// String

				writeString(this.nom_categorie, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.id_categorie);

				// String

				writeString(this.nom_categorie, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_categorie=" + String.valueOf(id_categorie));
			sb.append(",nom_categorie=" + nom_categorie);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CT_categorieStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id_categorie, other.id_categorie);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];

		public Integer id_categorie;

		public Integer getId_categorie() {
			return this.id_categorie;
		}

		public String nom_categorie;

		public String getNom_categorie() {
			return this.nom_categorie;
		}

		public String description;

		public String getDescription() {
			return this.description;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_categorie = readInteger(dis);

					this.nom_categorie = readString(dis);

					this.description = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_categorie = readInteger(dis);

					this.nom_categorie = readString(dis);

					this.description = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id_categorie, dos);

				// String

				writeString(this.nom_categorie, dos);

				// String

				writeString(this.description, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id_categorie, dos);

				// String

				writeString(this.nom_categorie, dos);

				// String

				writeString(this.description, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_categorie=" + String.valueOf(id_categorie));
			sb.append(",nom_categorie=" + nom_categorie);
			sb.append(",description=" + description);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row2Struct row2 = new row2Struct();
				CT_categorieStruct CT_categorie = new CT_categorieStruct();

				/**
				 * [tDBOutput_2 begin ] start
				 */

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "CT_categorie");
				}

				int tos_count_tDBOutput_2 = 0;

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;

				String tableName_tDBOutput_2 = "Dim_categorie";
				boolean whetherReject_tDBOutput_2 = false;

				java.util.Calendar calendar_tDBOutput_2 = java.util.Calendar.getInstance();
				calendar_tDBOutput_2.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
				calendar_tDBOutput_2.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
				long date_tDBOutput_2;

				java.sql.Connection conn_tDBOutput_2 = null;

				String properties_tDBOutput_2 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_2 == null || properties_tDBOutput_2.trim().length() == 0) {
					properties_tDBOutput_2 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_2.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_2 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_2.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_2 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_2 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "olap" + "?"
						+ properties_tDBOutput_2;

				String driverClass_tDBOutput_2 = "com.mysql.cj.jdbc.Driver";

				String dbUser_tDBOutput_2 = "root";

				final String decryptedPassword_tDBOutput_2 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:fNrlBOXilAj92SrWFGaW+z1Xu7GHX8rdirJ+mQ==");

				String dbPwd_tDBOutput_2 = decryptedPassword_tDBOutput_2;
				java.lang.Class.forName(driverClass_tDBOutput_2);

				conn_tDBOutput_2 = java.sql.DriverManager.getConnection(url_tDBOutput_2, dbUser_tDBOutput_2,
						dbPwd_tDBOutput_2);

				resourceMap.put("conn_tDBOutput_2", conn_tDBOutput_2);
				conn_tDBOutput_2.setAutoCommit(false);
				int commitEvery_tDBOutput_2 = 10000;
				int commitCounter_tDBOutput_2 = 0;

				int count_tDBOutput_2 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_2 = conn_tDBOutput_2.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_2 = dbMetaData_tDBOutput_2.getTables("olap", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_2 = false;
				while (rsTable_tDBOutput_2.next()) {
					String table_tDBOutput_2 = rsTable_tDBOutput_2.getString("TABLE_NAME");
					if (table_tDBOutput_2.equalsIgnoreCase("Dim_categorie")) {
						whetherExist_tDBOutput_2 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_2) {
					try (java.sql.Statement stmtDrop_tDBOutput_2 = conn_tDBOutput_2.createStatement()) {
						stmtDrop_tDBOutput_2.execute("DROP TABLE `" + tableName_tDBOutput_2 + "`");
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_2 = conn_tDBOutput_2.createStatement()) {
					stmtCreate_tDBOutput_2.execute("CREATE TABLE `" + tableName_tDBOutput_2
							+ "`(`id_categorie` VARCHAR(200)   not null ,`nom_categorie` VARCHAR(200)  ,primary key(`id_categorie`))");
				}

				String insert_tDBOutput_2 = "INSERT INTO `" + "Dim_categorie"
						+ "` (`id_categorie`,`nom_categorie`) VALUES (?,?)";
				int batchSize_tDBOutput_2 = 100;
				int batchSizeCounter_tDBOutput_2 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				ok_Hash.put("tMap_2", false);
				start_Hash.put("tMap_2", System.currentTimeMillis());

				currentComponent = "tMap_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
				}

				int tos_count_tMap_2 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_2__Struct {
				}
				Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				CT_categorieStruct CT_categorie_tmp = new CT_categorieStruct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				int tos_count_tDBInput_1 = 0;

				java.util.Calendar calendar_tDBInput_1 = java.util.Calendar.getInstance();
				calendar_tDBInput_1.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_1 = calendar_tDBInput_1.getTime();
				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
				String dbUser_tDBInput_1 = "root";

				final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:rQlZgkI5uJKhwOR8qSSWmzd7gvoB4thA+uO1dg==");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				String properties_tDBInput_1 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_1 == null || properties_tDBInput_1.trim().length() == 0) {
					properties_tDBInput_1 = "";
				}
				String url_tDBInput_1 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "datawarehouse" + "?"
						+ properties_tDBInput_1;

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  `categories`.`id_categorie`, \n  `categories`.`nom_categorie`, \n  `categories`.`description`\nFROM `categories`"
						+ "";

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row2.id_categorie = null;
						} else {

							row2.id_categorie = rs_tDBInput_1.getInt(1);
							if (rs_tDBInput_1.wasNull()) {
								row2.id_categorie = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row2.nom_categorie = null;
						} else {

							row2.nom_categorie = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, false);
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row2.description = null;
						} else {

							row2.description = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
						}

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tMap_2 main ] start
						 */

						currentComponent = "tMap_2";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row2"

							);
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;

						// ###############################
						// # Input tables (lookups)
						boolean rejectedInnerJoin_tMap_2 = false;
						boolean mainRowRejected_tMap_2 = false;

						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
							// ###############################
							// # Output tables

							CT_categorie = null;

// # Output table : 'CT_categorie'
							CT_categorie_tmp.id_categorie = row2.id_categorie;
							CT_categorie_tmp.nom_categorie = row2.nom_categorie;
							CT_categorie = CT_categorie_tmp;
// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_2 = false;

						tos_count_tMap_2++;

						/**
						 * [tMap_2 main ] stop
						 */

						/**
						 * [tMap_2 process_data_begin ] start
						 */

						currentComponent = "tMap_2";

						/**
						 * [tMap_2 process_data_begin ] stop
						 */
// Start of branch "CT_categorie"
						if (CT_categorie != null) {

							/**
							 * [tDBOutput_2 main ] start
							 */

							currentComponent = "tDBOutput_2";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "CT_categorie"

								);
							}

							whetherReject_tDBOutput_2 = false;
							pstmt_tDBOutput_2.setInt(1, CT_categorie.id_categorie);

							if (CT_categorie.nom_categorie == null) {
								pstmt_tDBOutput_2.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_2.setString(2, CT_categorie.nom_categorie);
							}

							pstmt_tDBOutput_2.addBatch();
							nb_line_tDBOutput_2++;

							batchSizeCounter_tDBOutput_2++;
							if (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2) {
								try {
									int countSum_tDBOutput_2 = 0;
									for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_2 = 0;
									for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
									System.err.println(e.getMessage());
								}

								batchSizeCounter_tDBOutput_2 = 0;
							}
							commitCounter_tDBOutput_2++;

							if (commitEvery_tDBOutput_2 <= commitCounter_tDBOutput_2) {

								try {
									int countSum_tDBOutput_2 = 0;
									for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_2 = 0;
									for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
										countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
									}
									rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
									insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
									System.err.println(e.getMessage());

								}
								if (rowsToCommitCount_tDBOutput_2 != 0) {
								}
								conn_tDBOutput_2.commit();
								if (rowsToCommitCount_tDBOutput_2 != 0) {
									rowsToCommitCount_tDBOutput_2 = 0;
								}
								commitCounter_tDBOutput_2 = 0;

							}

							tos_count_tDBOutput_2++;

							/**
							 * [tDBOutput_2 main ] stop
							 */

							/**
							 * [tDBOutput_2 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_2";

							/**
							 * [tDBOutput_2 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_2 process_data_end ] start
							 */

							currentComponent = "tDBOutput_2";

							/**
							 * [tDBOutput_2 process_data_end ] stop
							 */

						} // End of branch "CT_categorie"

						/**
						 * [tMap_2 process_data_end ] start
						 */

						currentComponent = "tMap_2";

						/**
						 * [tMap_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
					if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {

						conn_tDBInput_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tMap_2 end ] start
				 */

				currentComponent = "tMap_2";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
				}

				ok_Hash.put("tMap_2", true);
				end_Hash.put("tMap_2", System.currentTimeMillis());

				/**
				 * [tMap_2 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					if (batchSizeCounter_tDBOutput_2 != 0) {
						int countSum_tDBOutput_2 = 0;

						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

						insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					}

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_2 = 0;

				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");

				}
				resourceMap.put("statementClosed_tDBOutput_2", true);
				if (commitCounter_tDBOutput_2 > 0 && rowsToCommitCount_tDBOutput_2 != 0) {

				}
				conn_tDBOutput_2.commit();
				if (commitCounter_tDBOutput_2 > 0 && rowsToCommitCount_tDBOutput_2 != 0) {

					rowsToCommitCount_tDBOutput_2 = 0;
				}
				commitCounter_tDBOutput_2 = 0;

				conn_tDBOutput_2.close();

				resourceMap.put("finish_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "CT_categorie");
				}

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tMap_2 finally ] start
				 */

				currentComponent = "tMap_2";

				/**
				 * [tMap_2 finally ] stop
				 */

				/**
				 * [tDBOutput_2 finally ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
						if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_2")) != null) {
							pstmtToClose_tDBOutput_2.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_2") == null) {
						java.sql.Connection ctn_tDBOutput_2 = null;
						if ((ctn_tDBOutput_2 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_2")) != null) {
							try {
								ctn_tDBOutput_2.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_2) {
								String errorMessage_tDBOutput_2 = "failed to close the connection in tDBOutput_2 :"
										+ sqlEx_tDBOutput_2.getMessage();
								System.err.println(errorMessage_tDBOutput_2);
							}
						}
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public static class CT_clientStruct implements routines.system.IPersistableRow<CT_clientStruct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int id_client;

		public int getId_client() {
			return this.id_client;
		}

		public String nom;

		public String getNom() {
			return this.nom;
		}

		public String ville;

		public String getVille() {
			return this.ville;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.id_client;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final CT_clientStruct other = (CT_clientStruct) obj;

			if (this.id_client != other.id_client)
				return false;

			return true;
		}

		public void copyDataTo(CT_clientStruct other) {

			other.id_client = this.id_client;
			other.nom = this.nom;
			other.ville = this.ville;

		}

		public void copyKeysDataTo(CT_clientStruct other) {

			other.id_client = this.id_client;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_client = dis.readInt();

					this.nom = readString(dis);

					this.ville = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_client = dis.readInt();

					this.nom = readString(dis);

					this.ville = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.id_client);

				// String

				writeString(this.nom, dos);

				// String

				writeString(this.ville, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.id_client);

				// String

				writeString(this.nom, dos);

				// String

				writeString(this.ville, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_client=" + String.valueOf(id_client));
			sb.append(",nom=" + nom);
			sb.append(",ville=" + ville);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CT_clientStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id_client, other.id_client);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];

		public Integer id_client;

		public Integer getId_client() {
			return this.id_client;
		}

		public String nom;

		public String getNom() {
			return this.nom;
		}

		public String email;

		public String getEmail() {
			return this.email;
		}

		public String ville;

		public String getVille() {
			return this.ville;
		}

		public Integer id_date_inscription;

		public Integer getId_date_inscription() {
			return this.id_date_inscription;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_client = readInteger(dis);

					this.nom = readString(dis);

					this.email = readString(dis);

					this.ville = readString(dis);

					this.id_date_inscription = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_client = readInteger(dis);

					this.nom = readString(dis);

					this.email = readString(dis);

					this.ville = readString(dis);

					this.id_date_inscription = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id_client, dos);

				// String

				writeString(this.nom, dos);

				// String

				writeString(this.email, dos);

				// String

				writeString(this.ville, dos);

				// Integer

				writeInteger(this.id_date_inscription, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id_client, dos);

				// String

				writeString(this.nom, dos);

				// String

				writeString(this.email, dos);

				// String

				writeString(this.ville, dos);

				// Integer

				writeInteger(this.id_date_inscription, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_client=" + String.valueOf(id_client));
			sb.append(",nom=" + nom);
			sb.append(",email=" + email);
			sb.append(",ville=" + ville);
			sb.append(",id_date_inscription=" + String.valueOf(id_date_inscription));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				CT_clientStruct CT_client = new CT_clientStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "CT_client");
				}

				int tos_count_tDBOutput_1 = 0;

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				String tableName_tDBOutput_1 = "Dim_client";
				boolean whetherReject_tDBOutput_1 = false;

				java.util.Calendar calendar_tDBOutput_1 = java.util.Calendar.getInstance();
				calendar_tDBOutput_1.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
				calendar_tDBOutput_1.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
				long date_tDBOutput_1;

				java.sql.Connection conn_tDBOutput_1 = null;

				String properties_tDBOutput_1 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_1 == null || properties_tDBOutput_1.trim().length() == 0) {
					properties_tDBOutput_1 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_1.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_1 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_1.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_1 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_1 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "olap" + "?"
						+ properties_tDBOutput_1;

				String driverClass_tDBOutput_1 = "com.mysql.cj.jdbc.Driver";

				String dbUser_tDBOutput_1 = "root";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:gQGOAlNSQ2iXp0zhDK3IJ3aYWLTosN6AXhixsA==");

				String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;
				java.lang.Class.forName(driverClass_tDBOutput_1);

				conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1,
						dbPwd_tDBOutput_1);

				resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);
				conn_tDBOutput_1.setAutoCommit(false);
				int commitEvery_tDBOutput_1 = 10000;
				int commitCounter_tDBOutput_1 = 0;

				int count_tDBOutput_1 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_1 = conn_tDBOutput_1.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_1 = dbMetaData_tDBOutput_1.getTables("olap", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_1 = false;
				while (rsTable_tDBOutput_1.next()) {
					String table_tDBOutput_1 = rsTable_tDBOutput_1.getString("TABLE_NAME");
					if (table_tDBOutput_1.equalsIgnoreCase("Dim_client")) {
						whetherExist_tDBOutput_1 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_1) {
					try (java.sql.Statement stmtDrop_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
						stmtDrop_tDBOutput_1.execute("DROP TABLE `" + tableName_tDBOutput_1 + "`");
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
					stmtCreate_tDBOutput_1.execute("CREATE TABLE `" + tableName_tDBOutput_1
							+ "`(`id_client` VARCHAR(200)   not null ,`nom` VARCHAR(200)  ,`ville` VARCHAR(200)  ,primary key(`id_client`))");
				}

				String insert_tDBOutput_1 = "INSERT INTO `" + "Dim_client"
						+ "` (`id_client`,`nom`,`ville`) VALUES (?,?,?)";
				int batchSize_tDBOutput_1 = 100;
				int batchSizeCounter_tDBOutput_1 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
				}

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				CT_clientStruct CT_client_tmp = new CT_clientStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tDBInput_2 begin ] start
				 */

				ok_Hash.put("tDBInput_2", false);
				start_Hash.put("tDBInput_2", System.currentTimeMillis());

				currentComponent = "tDBInput_2";

				int tos_count_tDBInput_2 = 0;

				java.util.Calendar calendar_tDBInput_2 = java.util.Calendar.getInstance();
				calendar_tDBInput_2.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_2 = calendar_tDBInput_2.getTime();
				int nb_line_tDBInput_2 = 0;
				java.sql.Connection conn_tDBInput_2 = null;
				String driverClass_tDBInput_2 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
				String dbUser_tDBInput_2 = "root";

				final String decryptedPassword_tDBInput_2 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:v1SHr/J45wCk9fB4RdBojWoGZ+mRoCpbQd4xzA==");

				String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;

				String properties_tDBInput_2 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_2 == null || properties_tDBInput_2.trim().length() == 0) {
					properties_tDBInput_2 = "";
				}
				String url_tDBInput_2 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "datawarehouse" + "?"
						+ properties_tDBInput_2;

				conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2,
						dbPwd_tDBInput_2);

				java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

				String dbquery_tDBInput_2 = "SELECT \n  `clients`.`id_client`, \n  `clients`.`nom`, \n  `clients`.`email`, \n  `clients`.`ville`, \n  `clients`.`id_date_"
						+ "inscription`\nFROM `clients`";

				globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);
				java.sql.ResultSet rs_tDBInput_2 = null;

				try {
					rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
					java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
					int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

					String tmpContent_tDBInput_2 = null;

					while (rs_tDBInput_2.next()) {
						nb_line_tDBInput_2++;

						if (colQtyInRs_tDBInput_2 < 1) {
							row1.id_client = null;
						} else {

							row1.id_client = rs_tDBInput_2.getInt(1);
							if (rs_tDBInput_2.wasNull()) {
								row1.id_client = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row1.nom = null;
						} else {

							row1.nom = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
						}
						if (colQtyInRs_tDBInput_2 < 3) {
							row1.email = null;
						} else {

							row1.email = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
						}
						if (colQtyInRs_tDBInput_2 < 4) {
							row1.ville = null;
						} else {

							row1.ville = routines.system.JDBCUtil.getString(rs_tDBInput_2, 4, false);
						}
						if (colQtyInRs_tDBInput_2 < 5) {
							row1.id_date_inscription = null;
						} else {

							row1.id_date_inscription = rs_tDBInput_2.getInt(5);
							if (rs_tDBInput_2.wasNull()) {
								row1.id_date_inscription = null;
							}
						}

						/**
						 * [tDBInput_2 begin ] stop
						 */

						/**
						 * [tDBInput_2 main ] start
						 */

						currentComponent = "tDBInput_2";

						tos_count_tDBInput_2++;

						/**
						 * [tDBInput_2 main ] stop
						 */

						/**
						 * [tDBInput_2 process_data_begin ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_begin ] stop
						 */

						/**
						 * [tMap_1 main ] start
						 */

						currentComponent = "tMap_1";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row1"

							);
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

						// ###############################
						// # Input tables (lookups)
						boolean rejectedInnerJoin_tMap_1 = false;
						boolean mainRowRejected_tMap_1 = false;

						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
							// ###############################
							// # Output tables

							CT_client = null;

// # Output table : 'CT_client'
							CT_client_tmp.id_client = row1.id_client;
							CT_client_tmp.nom = row1.nom;
							CT_client_tmp.ville = row1.ville;
							CT_client = CT_client_tmp;
// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_1 = false;

						tos_count_tMap_1++;

						/**
						 * [tMap_1 main ] stop
						 */

						/**
						 * [tMap_1 process_data_begin ] start
						 */

						currentComponent = "tMap_1";

						/**
						 * [tMap_1 process_data_begin ] stop
						 */
// Start of branch "CT_client"
						if (CT_client != null) {

							/**
							 * [tDBOutput_1 main ] start
							 */

							currentComponent = "tDBOutput_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "CT_client"

								);
							}

							whetherReject_tDBOutput_1 = false;
							pstmt_tDBOutput_1.setInt(1, CT_client.id_client);

							if (CT_client.nom == null) {
								pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_1.setString(2, CT_client.nom);
							}

							if (CT_client.ville == null) {
								pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_1.setString(3, CT_client.ville);
							}

							pstmt_tDBOutput_1.addBatch();
							nb_line_tDBOutput_1++;

							batchSizeCounter_tDBOutput_1++;
							if (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1) {
								try {
									int countSum_tDBOutput_1 = 0;
									for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
										countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
									insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_1 = 0;
									for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
										countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
									}
									rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
									insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
									System.err.println(e.getMessage());
								}

								batchSizeCounter_tDBOutput_1 = 0;
							}
							commitCounter_tDBOutput_1++;

							if (commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {

								try {
									int countSum_tDBOutput_1 = 0;
									for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
										countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
									insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_1 = 0;
									for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
										countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
									}
									rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
									insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
									System.err.println(e.getMessage());

								}
								if (rowsToCommitCount_tDBOutput_1 != 0) {
								}
								conn_tDBOutput_1.commit();
								if (rowsToCommitCount_tDBOutput_1 != 0) {
									rowsToCommitCount_tDBOutput_1 = 0;
								}
								commitCounter_tDBOutput_1 = 0;

							}

							tos_count_tDBOutput_1++;

							/**
							 * [tDBOutput_1 main ] stop
							 */

							/**
							 * [tDBOutput_1 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_1";

							/**
							 * [tDBOutput_1 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_1 process_data_end ] start
							 */

							currentComponent = "tDBOutput_1";

							/**
							 * [tDBOutput_1 process_data_end ] stop
							 */

						} // End of branch "CT_client"

						/**
						 * [tMap_1 process_data_end ] start
						 */

						currentComponent = "tMap_1";

						/**
						 * [tMap_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 process_data_end ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 end ] start
						 */

						currentComponent = "tDBInput_2";

					}
				} finally {
					if (rs_tDBInput_2 != null) {
						rs_tDBInput_2.close();
					}
					if (stmt_tDBInput_2 != null) {
						stmt_tDBInput_2.close();
					}
					if (conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {

						conn_tDBInput_2.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);

				ok_Hash.put("tDBInput_2", true);
				end_Hash.put("tDBInput_2", System.currentTimeMillis());

				/**
				 * [tDBInput_2 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
				}

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					if (batchSizeCounter_tDBOutput_1 != 0) {
						int countSum_tDBOutput_1 = 0;

						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

						insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					}

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_1 = 0;

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");

				}
				resourceMap.put("statementClosed_tDBOutput_1", true);
				if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {

				}
				conn_tDBOutput_1.commit();
				if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {

					rowsToCommitCount_tDBOutput_1 = 0;
				}
				commitCounter_tDBOutput_1 = 0;

				conn_tDBOutput_1.close();

				resourceMap.put("finish_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "CT_client");
				}

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_2 finally ] start
				 */

				currentComponent = "tDBInput_2";

				/**
				 * [tDBInput_2 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
						if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_1")) != null) {
							pstmtToClose_tDBOutput_1.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_1") == null) {
						java.sql.Connection ctn_tDBOutput_1 = null;
						if ((ctn_tDBOutput_1 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_1")) != null) {
							try {
								ctn_tDBOutput_1.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_1) {
								String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :"
										+ sqlEx_tDBOutput_1.getMessage();
								System.err.println(errorMessage_tDBOutput_1);
							}
						}
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}

	public static class CT_dateStruct implements routines.system.IPersistableRow<CT_dateStruct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer id_date;

		public Integer getId_date() {
			return this.id_date;
		}

		public Integer jour;

		public Integer getJour() {
			return this.jour;
		}

		public Integer mois;

		public Integer getMois() {
			return this.mois;
		}

		public Integer annee;

		public Integer getAnnee() {
			return this.annee;
		}

		public String jour_semaine;

		public String getJour_semaine() {
			return this.jour_semaine;
		}

		public java.util.Date date_formatee;

		public java.util.Date getDate_formatee() {
			return this.date_formatee;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id_date == null) ? 0 : this.id_date.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final CT_dateStruct other = (CT_dateStruct) obj;

			if (this.id_date == null) {
				if (other.id_date != null)
					return false;

			} else if (!this.id_date.equals(other.id_date))

				return false;

			return true;
		}

		public void copyDataTo(CT_dateStruct other) {

			other.id_date = this.id_date;
			other.jour = this.jour;
			other.mois = this.mois;
			other.annee = this.annee;
			other.jour_semaine = this.jour_semaine;
			other.date_formatee = this.date_formatee;

		}

		public void copyKeysDataTo(CT_dateStruct other) {

			other.id_date = this.id_date;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_date = readInteger(dis);

					this.jour = readInteger(dis);

					this.mois = readInteger(dis);

					this.annee = readInteger(dis);

					this.jour_semaine = readString(dis);

					this.date_formatee = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_date = readInteger(dis);

					this.jour = readInteger(dis);

					this.mois = readInteger(dis);

					this.annee = readInteger(dis);

					this.jour_semaine = readString(dis);

					this.date_formatee = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id_date, dos);

				// Integer

				writeInteger(this.jour, dos);

				// Integer

				writeInteger(this.mois, dos);

				// Integer

				writeInteger(this.annee, dos);

				// String

				writeString(this.jour_semaine, dos);

				// java.util.Date

				writeDate(this.date_formatee, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id_date, dos);

				// Integer

				writeInteger(this.jour, dos);

				// Integer

				writeInteger(this.mois, dos);

				// Integer

				writeInteger(this.annee, dos);

				// String

				writeString(this.jour_semaine, dos);

				// java.util.Date

				writeDate(this.date_formatee, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_date=" + String.valueOf(id_date));
			sb.append(",jour=" + String.valueOf(jour));
			sb.append(",mois=" + String.valueOf(mois));
			sb.append(",annee=" + String.valueOf(annee));
			sb.append(",jour_semaine=" + jour_semaine);
			sb.append(",date_formatee=" + String.valueOf(date_formatee));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CT_dateStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id_date, other.id_date);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];

		public Integer id_date;

		public Integer getId_date() {
			return this.id_date;
		}

		public Integer jour;

		public Integer getJour() {
			return this.jour;
		}

		public Integer mois;

		public Integer getMois() {
			return this.mois;
		}

		public Integer annee;

		public Integer getAnnee() {
			return this.annee;
		}

		public String jour_semaine;

		public String getJour_semaine() {
			return this.jour_semaine;
		}

		public java.util.Date date_formatee;

		public java.util.Date getDate_formatee() {
			return this.date_formatee;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_date = readInteger(dis);

					this.jour = readInteger(dis);

					this.mois = readInteger(dis);

					this.annee = readInteger(dis);

					this.jour_semaine = readString(dis);

					this.date_formatee = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_date = readInteger(dis);

					this.jour = readInteger(dis);

					this.mois = readInteger(dis);

					this.annee = readInteger(dis);

					this.jour_semaine = readString(dis);

					this.date_formatee = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id_date, dos);

				// Integer

				writeInteger(this.jour, dos);

				// Integer

				writeInteger(this.mois, dos);

				// Integer

				writeInteger(this.annee, dos);

				// String

				writeString(this.jour_semaine, dos);

				// java.util.Date

				writeDate(this.date_formatee, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id_date, dos);

				// Integer

				writeInteger(this.jour, dos);

				// Integer

				writeInteger(this.mois, dos);

				// Integer

				writeInteger(this.annee, dos);

				// String

				writeString(this.jour_semaine, dos);

				// java.util.Date

				writeDate(this.date_formatee, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_date=" + String.valueOf(id_date));
			sb.append(",jour=" + String.valueOf(jour));
			sb.append(",mois=" + String.valueOf(mois));
			sb.append(",annee=" + String.valueOf(annee));
			sb.append(",jour_semaine=" + jour_semaine);
			sb.append(",date_formatee=" + String.valueOf(date_formatee));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row3Struct row3 = new row3Struct();
				CT_dateStruct CT_date = new CT_dateStruct();

				/**
				 * [tDBOutput_3 begin ] start
				 */

				ok_Hash.put("tDBOutput_3", false);
				start_Hash.put("tDBOutput_3", System.currentTimeMillis());

				currentComponent = "tDBOutput_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "CT_date");
				}

				int tos_count_tDBOutput_3 = 0;

				int nb_line_tDBOutput_3 = 0;
				int nb_line_update_tDBOutput_3 = 0;
				int nb_line_inserted_tDBOutput_3 = 0;
				int nb_line_deleted_tDBOutput_3 = 0;
				int nb_line_rejected_tDBOutput_3 = 0;

				int deletedCount_tDBOutput_3 = 0;
				int updatedCount_tDBOutput_3 = 0;
				int insertedCount_tDBOutput_3 = 0;
				int rowsToCommitCount_tDBOutput_3 = 0;
				int rejectedCount_tDBOutput_3 = 0;

				String tableName_tDBOutput_3 = "Dim_date";
				boolean whetherReject_tDBOutput_3 = false;

				java.util.Calendar calendar_tDBOutput_3 = java.util.Calendar.getInstance();
				calendar_tDBOutput_3.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
				calendar_tDBOutput_3.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
				long date_tDBOutput_3;

				java.sql.Connection conn_tDBOutput_3 = null;

				String properties_tDBOutput_3 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_3 == null || properties_tDBOutput_3.trim().length() == 0) {
					properties_tDBOutput_3 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_3.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_3 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_3.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_3 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_3 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "olap" + "?"
						+ properties_tDBOutput_3;

				String driverClass_tDBOutput_3 = "com.mysql.cj.jdbc.Driver";

				String dbUser_tDBOutput_3 = "root";

				final String decryptedPassword_tDBOutput_3 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:fjsuXKlwR5gp/BHx8WFOxK0nCMJdBJ28egWI1w==");

				String dbPwd_tDBOutput_3 = decryptedPassword_tDBOutput_3;
				java.lang.Class.forName(driverClass_tDBOutput_3);

				conn_tDBOutput_3 = java.sql.DriverManager.getConnection(url_tDBOutput_3, dbUser_tDBOutput_3,
						dbPwd_tDBOutput_3);

				resourceMap.put("conn_tDBOutput_3", conn_tDBOutput_3);
				conn_tDBOutput_3.setAutoCommit(false);
				int commitEvery_tDBOutput_3 = 10000;
				int commitCounter_tDBOutput_3 = 0;

				int count_tDBOutput_3 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_3 = conn_tDBOutput_3.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_3 = dbMetaData_tDBOutput_3.getTables("olap", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_3 = false;
				while (rsTable_tDBOutput_3.next()) {
					String table_tDBOutput_3 = rsTable_tDBOutput_3.getString("TABLE_NAME");
					if (table_tDBOutput_3.equalsIgnoreCase("Dim_date")) {
						whetherExist_tDBOutput_3 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_3) {
					try (java.sql.Statement stmtDrop_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
						stmtDrop_tDBOutput_3.execute("DROP TABLE `" + tableName_tDBOutput_3 + "`");
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
					stmtCreate_tDBOutput_3.execute("CREATE TABLE `" + tableName_tDBOutput_3
							+ "`(`id_date` INT(10)  ,`jour` INT(10)  ,`mois` INT(10)  ,`annee` INT(10)  ,`jour_semaine` VARCHAR(9)  ,`date_formatee` DATE ,primary key(`id_date`))");
				}

				String insert_tDBOutput_3 = "INSERT INTO `" + "Dim_date"
						+ "` (`id_date`,`jour`,`mois`,`annee`,`jour_semaine`,`date_formatee`) VALUES (?,?,?,?,?,?)";
				int batchSize_tDBOutput_3 = 100;
				int batchSizeCounter_tDBOutput_3 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(insert_tDBOutput_3);
				resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);

				/**
				 * [tDBOutput_3 begin ] stop
				 */

				/**
				 * [tMap_3 begin ] start
				 */

				ok_Hash.put("tMap_3", false);
				start_Hash.put("tMap_3", System.currentTimeMillis());

				currentComponent = "tMap_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row3");
				}

				int tos_count_tMap_3 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_3__Struct {
				}
				Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
				CT_dateStruct CT_date_tmp = new CT_dateStruct();
// ###############################

				/**
				 * [tMap_3 begin ] stop
				 */

				/**
				 * [tDBInput_3 begin ] start
				 */

				ok_Hash.put("tDBInput_3", false);
				start_Hash.put("tDBInput_3", System.currentTimeMillis());

				currentComponent = "tDBInput_3";

				int tos_count_tDBInput_3 = 0;

				java.util.Calendar calendar_tDBInput_3 = java.util.Calendar.getInstance();
				calendar_tDBInput_3.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_3 = calendar_tDBInput_3.getTime();
				int nb_line_tDBInput_3 = 0;
				java.sql.Connection conn_tDBInput_3 = null;
				String driverClass_tDBInput_3 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_3 = java.lang.Class.forName(driverClass_tDBInput_3);
				String dbUser_tDBInput_3 = "root";

				final String decryptedPassword_tDBInput_3 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:pu+gn+yAQ3LEiDlq8dMR06ooSCuAS/OsAR/IqA==");

				String dbPwd_tDBInput_3 = decryptedPassword_tDBInput_3;

				String properties_tDBInput_3 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_3 == null || properties_tDBInput_3.trim().length() == 0) {
					properties_tDBInput_3 = "";
				}
				String url_tDBInput_3 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "datawarehouse" + "?"
						+ properties_tDBInput_3;

				conn_tDBInput_3 = java.sql.DriverManager.getConnection(url_tDBInput_3, dbUser_tDBInput_3,
						dbPwd_tDBInput_3);

				java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();

				String dbquery_tDBInput_3 = "SELECT \n  `dates`.`id_date`, \n  `dates`.`jour`, \n  `dates`.`mois`, \n  `dates`.`annee`, \n  `dates`.`jour_semaine`, \n  `d"
						+ "ates`.`date_formatee`\nFROM `dates`";

				globalMap.put("tDBInput_3_QUERY", dbquery_tDBInput_3);
				java.sql.ResultSet rs_tDBInput_3 = null;

				try {
					rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
					java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
					int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();

					String tmpContent_tDBInput_3 = null;

					while (rs_tDBInput_3.next()) {
						nb_line_tDBInput_3++;

						if (colQtyInRs_tDBInput_3 < 1) {
							row3.id_date = null;
						} else {

							row3.id_date = rs_tDBInput_3.getInt(1);
							if (rs_tDBInput_3.wasNull()) {
								row3.id_date = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 2) {
							row3.jour = null;
						} else {

							row3.jour = rs_tDBInput_3.getInt(2);
							if (rs_tDBInput_3.wasNull()) {
								row3.jour = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 3) {
							row3.mois = null;
						} else {

							row3.mois = rs_tDBInput_3.getInt(3);
							if (rs_tDBInput_3.wasNull()) {
								row3.mois = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 4) {
							row3.annee = null;
						} else {

							row3.annee = rs_tDBInput_3.getInt(4);
							if (rs_tDBInput_3.wasNull()) {
								row3.annee = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 5) {
							row3.jour_semaine = null;
						} else {

							row3.jour_semaine = routines.system.JDBCUtil.getString(rs_tDBInput_3, 5, false);
						}
						if (colQtyInRs_tDBInput_3 < 6) {
							row3.date_formatee = null;
						} else {

							if (rs_tDBInput_3.getString(6) != null) {
								String dateString_tDBInput_3 = rs_tDBInput_3.getString(6);
								if (!("0000-00-00").equals(dateString_tDBInput_3)
										&& !("0000-00-00 00:00:00").equals(dateString_tDBInput_3)) {
									row3.date_formatee = rs_tDBInput_3.getTimestamp(6);
								} else {
									row3.date_formatee = (java.util.Date) year0_tDBInput_3.clone();
								}
							} else {
								row3.date_formatee = null;
							}
						}

						/**
						 * [tDBInput_3 begin ] stop
						 */

						/**
						 * [tDBInput_3 main ] start
						 */

						currentComponent = "tDBInput_3";

						tos_count_tDBInput_3++;

						/**
						 * [tDBInput_3 main ] stop
						 */

						/**
						 * [tDBInput_3 process_data_begin ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_begin ] stop
						 */

						/**
						 * [tMap_3 main ] start
						 */

						currentComponent = "tMap_3";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row3"

							);
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_3 = false;

						// ###############################
						// # Input tables (lookups)
						boolean rejectedInnerJoin_tMap_3 = false;
						boolean mainRowRejected_tMap_3 = false;

						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_3__Struct Var = Var__tMap_3;// ###############################
							// ###############################
							// # Output tables

							CT_date = null;

// # Output table : 'CT_date'
							CT_date_tmp.id_date = row3.id_date;
							CT_date_tmp.jour = row3.jour;
							CT_date_tmp.mois = row3.mois;
							CT_date_tmp.annee = row3.annee;
							CT_date_tmp.jour_semaine = row3.jour_semaine;
							CT_date_tmp.date_formatee = row3.date_formatee;
							CT_date = CT_date_tmp;
// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_3 = false;

						tos_count_tMap_3++;

						/**
						 * [tMap_3 main ] stop
						 */

						/**
						 * [tMap_3 process_data_begin ] start
						 */

						currentComponent = "tMap_3";

						/**
						 * [tMap_3 process_data_begin ] stop
						 */
// Start of branch "CT_date"
						if (CT_date != null) {

							/**
							 * [tDBOutput_3 main ] start
							 */

							currentComponent = "tDBOutput_3";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "CT_date"

								);
							}

							whetherReject_tDBOutput_3 = false;
							if (CT_date.id_date == null) {
								pstmt_tDBOutput_3.setNull(1, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_3.setInt(1, CT_date.id_date);
							}

							if (CT_date.jour == null) {
								pstmt_tDBOutput_3.setNull(2, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_3.setInt(2, CT_date.jour);
							}

							if (CT_date.mois == null) {
								pstmt_tDBOutput_3.setNull(3, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_3.setInt(3, CT_date.mois);
							}

							if (CT_date.annee == null) {
								pstmt_tDBOutput_3.setNull(4, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_3.setInt(4, CT_date.annee);
							}

							if (CT_date.jour_semaine == null) {
								pstmt_tDBOutput_3.setNull(5, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_3.setString(5, CT_date.jour_semaine);
							}

							if (CT_date.date_formatee != null) {
								date_tDBOutput_3 = CT_date.date_formatee.getTime();
								if (date_tDBOutput_3 < year1_tDBOutput_3 || date_tDBOutput_3 >= year10000_tDBOutput_3) {
									pstmt_tDBOutput_3.setString(6, "0000-00-00 00:00:00");
								} else {
									pstmt_tDBOutput_3.setTimestamp(6, new java.sql.Timestamp(date_tDBOutput_3));
								}
							} else {
								pstmt_tDBOutput_3.setNull(6, java.sql.Types.DATE);
							}

							pstmt_tDBOutput_3.addBatch();
							nb_line_tDBOutput_3++;

							batchSizeCounter_tDBOutput_3++;
							if (batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3) {
								try {
									int countSum_tDBOutput_3 = 0;
									for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_3 = 0;
									for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
									System.err.println(e.getMessage());
								}

								batchSizeCounter_tDBOutput_3 = 0;
							}
							commitCounter_tDBOutput_3++;

							if (commitEvery_tDBOutput_3 <= commitCounter_tDBOutput_3) {

								try {
									int countSum_tDBOutput_3 = 0;
									for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_3 = 0;
									for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
										countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
									}
									rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
									insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
									System.err.println(e.getMessage());

								}
								if (rowsToCommitCount_tDBOutput_3 != 0) {
								}
								conn_tDBOutput_3.commit();
								if (rowsToCommitCount_tDBOutput_3 != 0) {
									rowsToCommitCount_tDBOutput_3 = 0;
								}
								commitCounter_tDBOutput_3 = 0;

							}

							tos_count_tDBOutput_3++;

							/**
							 * [tDBOutput_3 main ] stop
							 */

							/**
							 * [tDBOutput_3 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_3";

							/**
							 * [tDBOutput_3 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_3 process_data_end ] start
							 */

							currentComponent = "tDBOutput_3";

							/**
							 * [tDBOutput_3 process_data_end ] stop
							 */

						} // End of branch "CT_date"

						/**
						 * [tMap_3 process_data_end ] start
						 */

						currentComponent = "tMap_3";

						/**
						 * [tMap_3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 process_data_end ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 end ] start
						 */

						currentComponent = "tDBInput_3";

					}
				} finally {
					if (rs_tDBInput_3 != null) {
						rs_tDBInput_3.close();
					}
					if (stmt_tDBInput_3 != null) {
						stmt_tDBInput_3.close();
					}
					if (conn_tDBInput_3 != null && !conn_tDBInput_3.isClosed()) {

						conn_tDBInput_3.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				globalMap.put("tDBInput_3_NB_LINE", nb_line_tDBInput_3);

				ok_Hash.put("tDBInput_3", true);
				end_Hash.put("tDBInput_3", System.currentTimeMillis());

				/**
				 * [tDBInput_3 end ] stop
				 */

				/**
				 * [tMap_3 end ] start
				 */

				currentComponent = "tMap_3";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row3");
				}

				ok_Hash.put("tMap_3", true);
				end_Hash.put("tMap_3", System.currentTimeMillis());

				/**
				 * [tMap_3 end ] stop
				 */

				/**
				 * [tDBOutput_3 end ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					if (batchSizeCounter_tDBOutput_3 != 0) {
						int countSum_tDBOutput_3 = 0;

						for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
							countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

						insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					}

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_3 = 0;
					for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
						countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
					}
					rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_3 = 0;

				if (pstmt_tDBOutput_3 != null) {

					pstmt_tDBOutput_3.close();
					resourceMap.remove("pstmt_tDBOutput_3");

				}
				resourceMap.put("statementClosed_tDBOutput_3", true);
				if (commitCounter_tDBOutput_3 > 0 && rowsToCommitCount_tDBOutput_3 != 0) {

				}
				conn_tDBOutput_3.commit();
				if (commitCounter_tDBOutput_3 > 0 && rowsToCommitCount_tDBOutput_3 != 0) {

					rowsToCommitCount_tDBOutput_3 = 0;
				}
				commitCounter_tDBOutput_3 = 0;

				conn_tDBOutput_3.close();

				resourceMap.put("finish_tDBOutput_3", true);

				nb_line_deleted_tDBOutput_3 = nb_line_deleted_tDBOutput_3 + deletedCount_tDBOutput_3;
				nb_line_update_tDBOutput_3 = nb_line_update_tDBOutput_3 + updatedCount_tDBOutput_3;
				nb_line_inserted_tDBOutput_3 = nb_line_inserted_tDBOutput_3 + insertedCount_tDBOutput_3;
				nb_line_rejected_tDBOutput_3 = nb_line_rejected_tDBOutput_3 + rejectedCount_tDBOutput_3;

				globalMap.put("tDBOutput_3_NB_LINE", nb_line_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_UPDATED", nb_line_update_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_DELETED", nb_line_deleted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_3);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "CT_date");
				}

				ok_Hash.put("tDBOutput_3", true);
				end_Hash.put("tDBOutput_3", System.currentTimeMillis());

				/**
				 * [tDBOutput_3 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_3 finally ] start
				 */

				currentComponent = "tDBInput_3";

				/**
				 * [tDBInput_3 finally ] stop
				 */

				/**
				 * [tMap_3 finally ] start
				 */

				currentComponent = "tMap_3";

				/**
				 * [tMap_3 finally ] stop
				 */

				/**
				 * [tDBOutput_3 finally ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_3") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_3 = null;
						if ((pstmtToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_3")) != null) {
							pstmtToClose_tDBOutput_3.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_3") == null) {
						java.sql.Connection ctn_tDBOutput_3 = null;
						if ((ctn_tDBOutput_3 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_3")) != null) {
							try {
								ctn_tDBOutput_3.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_3) {
								String errorMessage_tDBOutput_3 = "failed to close the connection in tDBOutput_3 :"
										+ sqlEx_tDBOutput_3.getMessage();
								System.err.println(errorMessage_tDBOutput_3);
							}
						}
					}
				}

				/**
				 * [tDBOutput_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
	}

	public static class CT_produitStruct implements routines.system.IPersistableRow<CT_produitStruct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int id_produit;

		public int getId_produit() {
			return this.id_produit;
		}

		public String nom_produit;

		public String getNom_produit() {
			return this.nom_produit;
		}

		public String marque;

		public String getMarque() {
			return this.marque;
		}

		public Integer id_categorie;

		public Integer getId_categorie() {
			return this.id_categorie;
		}

		public Integer id_sous_categorie;

		public Integer getId_sous_categorie() {
			return this.id_sous_categorie;
		}

		public Double prix_unitaire;

		public Double getPrix_unitaire() {
			return this.prix_unitaire;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.id_produit;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final CT_produitStruct other = (CT_produitStruct) obj;

			if (this.id_produit != other.id_produit)
				return false;

			return true;
		}

		public void copyDataTo(CT_produitStruct other) {

			other.id_produit = this.id_produit;
			other.nom_produit = this.nom_produit;
			other.marque = this.marque;
			other.id_categorie = this.id_categorie;
			other.id_sous_categorie = this.id_sous_categorie;
			other.prix_unitaire = this.prix_unitaire;

		}

		public void copyKeysDataTo(CT_produitStruct other) {

			other.id_produit = this.id_produit;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_produit = dis.readInt();

					this.nom_produit = readString(dis);

					this.marque = readString(dis);

					this.id_categorie = readInteger(dis);

					this.id_sous_categorie = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.prix_unitaire = null;
					} else {
						this.prix_unitaire = dis.readDouble();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_produit = dis.readInt();

					this.nom_produit = readString(dis);

					this.marque = readString(dis);

					this.id_categorie = readInteger(dis);

					this.id_sous_categorie = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.prix_unitaire = null;
					} else {
						this.prix_unitaire = dis.readDouble();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.id_produit);

				// String

				writeString(this.nom_produit, dos);

				// String

				writeString(this.marque, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

				// Integer

				writeInteger(this.id_sous_categorie, dos);

				// Double

				if (this.prix_unitaire == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.prix_unitaire);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.id_produit);

				// String

				writeString(this.nom_produit, dos);

				// String

				writeString(this.marque, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

				// Integer

				writeInteger(this.id_sous_categorie, dos);

				// Double

				if (this.prix_unitaire == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.prix_unitaire);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_produit=" + String.valueOf(id_produit));
			sb.append(",nom_produit=" + nom_produit);
			sb.append(",marque=" + marque);
			sb.append(",id_categorie=" + String.valueOf(id_categorie));
			sb.append(",id_sous_categorie=" + String.valueOf(id_sous_categorie));
			sb.append(",prix_unitaire=" + String.valueOf(prix_unitaire));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CT_produitStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id_produit, other.id_produit);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];

		public Integer id_produit;

		public Integer getId_produit() {
			return this.id_produit;
		}

		public String nom_produit;

		public String getNom_produit() {
			return this.nom_produit;
		}

		public String marque;

		public String getMarque() {
			return this.marque;
		}

		public Integer id_categorie;

		public Integer getId_categorie() {
			return this.id_categorie;
		}

		public Integer id_sous_categorie;

		public Integer getId_sous_categorie() {
			return this.id_sous_categorie;
		}

		public Double prix_unitaire;

		public Double getPrix_unitaire() {
			return this.prix_unitaire;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_produit = readInteger(dis);

					this.nom_produit = readString(dis);

					this.marque = readString(dis);

					this.id_categorie = readInteger(dis);

					this.id_sous_categorie = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.prix_unitaire = null;
					} else {
						this.prix_unitaire = dis.readDouble();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_produit = readInteger(dis);

					this.nom_produit = readString(dis);

					this.marque = readString(dis);

					this.id_categorie = readInteger(dis);

					this.id_sous_categorie = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.prix_unitaire = null;
					} else {
						this.prix_unitaire = dis.readDouble();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id_produit, dos);

				// String

				writeString(this.nom_produit, dos);

				// String

				writeString(this.marque, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

				// Integer

				writeInteger(this.id_sous_categorie, dos);

				// Double

				if (this.prix_unitaire == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.prix_unitaire);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id_produit, dos);

				// String

				writeString(this.nom_produit, dos);

				// String

				writeString(this.marque, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

				// Integer

				writeInteger(this.id_sous_categorie, dos);

				// Double

				if (this.prix_unitaire == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.prix_unitaire);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_produit=" + String.valueOf(id_produit));
			sb.append(",nom_produit=" + nom_produit);
			sb.append(",marque=" + marque);
			sb.append(",id_categorie=" + String.valueOf(id_categorie));
			sb.append(",id_sous_categorie=" + String.valueOf(id_sous_categorie));
			sb.append(",prix_unitaire=" + String.valueOf(prix_unitaire));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row4Struct row4 = new row4Struct();
				CT_produitStruct CT_produit = new CT_produitStruct();

				/**
				 * [tDBOutput_4 begin ] start
				 */

				ok_Hash.put("tDBOutput_4", false);
				start_Hash.put("tDBOutput_4", System.currentTimeMillis());

				currentComponent = "tDBOutput_4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "CT_produit");
				}

				int tos_count_tDBOutput_4 = 0;

				int nb_line_tDBOutput_4 = 0;
				int nb_line_update_tDBOutput_4 = 0;
				int nb_line_inserted_tDBOutput_4 = 0;
				int nb_line_deleted_tDBOutput_4 = 0;
				int nb_line_rejected_tDBOutput_4 = 0;

				int deletedCount_tDBOutput_4 = 0;
				int updatedCount_tDBOutput_4 = 0;
				int insertedCount_tDBOutput_4 = 0;
				int rowsToCommitCount_tDBOutput_4 = 0;
				int rejectedCount_tDBOutput_4 = 0;

				String tableName_tDBOutput_4 = "Dim_produit";
				boolean whetherReject_tDBOutput_4 = false;

				java.util.Calendar calendar_tDBOutput_4 = java.util.Calendar.getInstance();
				calendar_tDBOutput_4.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
				calendar_tDBOutput_4.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
				long date_tDBOutput_4;

				java.sql.Connection conn_tDBOutput_4 = null;

				String properties_tDBOutput_4 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_4 == null || properties_tDBOutput_4.trim().length() == 0) {
					properties_tDBOutput_4 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_4.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_4 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_4.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_4 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_4 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "olap" + "?"
						+ properties_tDBOutput_4;

				String driverClass_tDBOutput_4 = "com.mysql.cj.jdbc.Driver";

				String dbUser_tDBOutput_4 = "root";

				final String decryptedPassword_tDBOutput_4 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:KR14l6Ngie6tPwuWmA8LnU+nbL99cy231oG34Q==");

				String dbPwd_tDBOutput_4 = decryptedPassword_tDBOutput_4;
				java.lang.Class.forName(driverClass_tDBOutput_4);

				conn_tDBOutput_4 = java.sql.DriverManager.getConnection(url_tDBOutput_4, dbUser_tDBOutput_4,
						dbPwd_tDBOutput_4);

				resourceMap.put("conn_tDBOutput_4", conn_tDBOutput_4);
				conn_tDBOutput_4.setAutoCommit(false);
				int commitEvery_tDBOutput_4 = 10000;
				int commitCounter_tDBOutput_4 = 0;

				int count_tDBOutput_4 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_4 = conn_tDBOutput_4.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_4 = dbMetaData_tDBOutput_4.getTables("olap", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_4 = false;
				while (rsTable_tDBOutput_4.next()) {
					String table_tDBOutput_4 = rsTable_tDBOutput_4.getString("TABLE_NAME");
					if (table_tDBOutput_4.equalsIgnoreCase("Dim_produit")) {
						whetherExist_tDBOutput_4 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_4) {
					try (java.sql.Statement stmtDrop_tDBOutput_4 = conn_tDBOutput_4.createStatement()) {
						stmtDrop_tDBOutput_4.execute("DROP TABLE `" + tableName_tDBOutput_4 + "`");
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_4 = conn_tDBOutput_4.createStatement()) {
					stmtCreate_tDBOutput_4.execute("CREATE TABLE `" + tableName_tDBOutput_4
							+ "`(`id_produit` VARCHAR(200)   not null ,`nom_produit` VARCHAR(200)  ,`marque` VARCHAR(200)  ,`id_categorie` VARCHAR(200)  ,`id_sous_categorie` VARCHAR(200)  ,`prix_unitaire;;;;;` VARCHAR(200)  ,primary key(`id_produit`))");
				}

				String insert_tDBOutput_4 = "INSERT INTO `" + "Dim_produit"
						+ "` (`id_produit`,`nom_produit`,`marque`,`id_categorie`,`id_sous_categorie`,`prix_unitaire;;;;;`) VALUES (?,?,?,?,?,?)";
				int batchSize_tDBOutput_4 = 100;
				int batchSizeCounter_tDBOutput_4 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_4 = conn_tDBOutput_4.prepareStatement(insert_tDBOutput_4);
				resourceMap.put("pstmt_tDBOutput_4", pstmt_tDBOutput_4);

				/**
				 * [tDBOutput_4 begin ] stop
				 */

				/**
				 * [tMap_4 begin ] start
				 */

				ok_Hash.put("tMap_4", false);
				start_Hash.put("tMap_4", System.currentTimeMillis());

				currentComponent = "tMap_4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row4");
				}

				int tos_count_tMap_4 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_4__Struct {
				}
				Var__tMap_4__Struct Var__tMap_4 = new Var__tMap_4__Struct();
// ###############################

// ###############################
// # Outputs initialization
				CT_produitStruct CT_produit_tmp = new CT_produitStruct();
// ###############################

				/**
				 * [tMap_4 begin ] stop
				 */

				/**
				 * [tDBInput_4 begin ] start
				 */

				ok_Hash.put("tDBInput_4", false);
				start_Hash.put("tDBInput_4", System.currentTimeMillis());

				currentComponent = "tDBInput_4";

				int tos_count_tDBInput_4 = 0;

				java.util.Calendar calendar_tDBInput_4 = java.util.Calendar.getInstance();
				calendar_tDBInput_4.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_4 = calendar_tDBInput_4.getTime();
				int nb_line_tDBInput_4 = 0;
				java.sql.Connection conn_tDBInput_4 = null;
				String driverClass_tDBInput_4 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_4 = java.lang.Class.forName(driverClass_tDBInput_4);
				String dbUser_tDBInput_4 = "root";

				final String decryptedPassword_tDBInput_4 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:w+Y6URkZBSJSJveS+9Oiv+5DnubComHN7nCOHg==");

				String dbPwd_tDBInput_4 = decryptedPassword_tDBInput_4;

				String properties_tDBInput_4 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_4 == null || properties_tDBInput_4.trim().length() == 0) {
					properties_tDBInput_4 = "";
				}
				String url_tDBInput_4 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "datawarehouse" + "?"
						+ properties_tDBInput_4;

				conn_tDBInput_4 = java.sql.DriverManager.getConnection(url_tDBInput_4, dbUser_tDBInput_4,
						dbPwd_tDBInput_4);

				java.sql.Statement stmt_tDBInput_4 = conn_tDBInput_4.createStatement();

				String dbquery_tDBInput_4 = "SELECT \n  `produits`.`id_produit`, \n  `produits`.`nom_produit`, \n  `produits`.`marque`, \n  `produits`.`id_categorie`, \n"
						+ "  `produits`.`id_sous_categorie`, \n  `produits`.`prix_unitaire`\nFROM `produits`";

				globalMap.put("tDBInput_4_QUERY", dbquery_tDBInput_4);
				java.sql.ResultSet rs_tDBInput_4 = null;

				try {
					rs_tDBInput_4 = stmt_tDBInput_4.executeQuery(dbquery_tDBInput_4);
					java.sql.ResultSetMetaData rsmd_tDBInput_4 = rs_tDBInput_4.getMetaData();
					int colQtyInRs_tDBInput_4 = rsmd_tDBInput_4.getColumnCount();

					String tmpContent_tDBInput_4 = null;

					while (rs_tDBInput_4.next()) {
						nb_line_tDBInput_4++;

						if (colQtyInRs_tDBInput_4 < 1) {
							row4.id_produit = null;
						} else {

							row4.id_produit = rs_tDBInput_4.getInt(1);
							if (rs_tDBInput_4.wasNull()) {
								row4.id_produit = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 2) {
							row4.nom_produit = null;
						} else {

							row4.nom_produit = routines.system.JDBCUtil.getString(rs_tDBInput_4, 2, false);
						}
						if (colQtyInRs_tDBInput_4 < 3) {
							row4.marque = null;
						} else {

							row4.marque = routines.system.JDBCUtil.getString(rs_tDBInput_4, 3, false);
						}
						if (colQtyInRs_tDBInput_4 < 4) {
							row4.id_categorie = null;
						} else {

							row4.id_categorie = rs_tDBInput_4.getInt(4);
							if (rs_tDBInput_4.wasNull()) {
								row4.id_categorie = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 5) {
							row4.id_sous_categorie = null;
						} else {

							row4.id_sous_categorie = rs_tDBInput_4.getInt(5);
							if (rs_tDBInput_4.wasNull()) {
								row4.id_sous_categorie = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 6) {
							row4.prix_unitaire = null;
						} else {

							row4.prix_unitaire = rs_tDBInput_4.getDouble(6);
							if (rs_tDBInput_4.wasNull()) {
								row4.prix_unitaire = null;
							}
						}

						/**
						 * [tDBInput_4 begin ] stop
						 */

						/**
						 * [tDBInput_4 main ] start
						 */

						currentComponent = "tDBInput_4";

						tos_count_tDBInput_4++;

						/**
						 * [tDBInput_4 main ] stop
						 */

						/**
						 * [tDBInput_4 process_data_begin ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_begin ] stop
						 */

						/**
						 * [tMap_4 main ] start
						 */

						currentComponent = "tMap_4";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row4"

							);
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_4 = false;

						// ###############################
						// # Input tables (lookups)
						boolean rejectedInnerJoin_tMap_4 = false;
						boolean mainRowRejected_tMap_4 = false;

						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_4__Struct Var = Var__tMap_4;// ###############################
							// ###############################
							// # Output tables

							CT_produit = null;

// # Output table : 'CT_produit'
							CT_produit_tmp.id_produit = row4.id_produit;
							CT_produit_tmp.nom_produit = row4.nom_produit;
							CT_produit_tmp.marque = row4.marque;
							CT_produit_tmp.id_categorie = row4.id_categorie;
							CT_produit_tmp.id_sous_categorie = row4.id_sous_categorie;
							CT_produit_tmp.prix_unitaire = row4.prix_unitaire;
							CT_produit = CT_produit_tmp;
// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_4 = false;

						tos_count_tMap_4++;

						/**
						 * [tMap_4 main ] stop
						 */

						/**
						 * [tMap_4 process_data_begin ] start
						 */

						currentComponent = "tMap_4";

						/**
						 * [tMap_4 process_data_begin ] stop
						 */
// Start of branch "CT_produit"
						if (CT_produit != null) {

							/**
							 * [tDBOutput_4 main ] start
							 */

							currentComponent = "tDBOutput_4";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "CT_produit"

								);
							}

							whetherReject_tDBOutput_4 = false;
							pstmt_tDBOutput_4.setInt(1, CT_produit.id_produit);

							if (CT_produit.nom_produit == null) {
								pstmt_tDBOutput_4.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_4.setString(2, CT_produit.nom_produit);
							}

							if (CT_produit.marque == null) {
								pstmt_tDBOutput_4.setNull(3, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_4.setString(3, CT_produit.marque);
							}

							if (CT_produit.id_categorie == null) {
								pstmt_tDBOutput_4.setNull(4, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_4.setInt(4, CT_produit.id_categorie);
							}

							if (CT_produit.id_sous_categorie == null) {
								pstmt_tDBOutput_4.setNull(5, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_4.setInt(5, CT_produit.id_sous_categorie);
							}

							if (CT_produit.prix_unitaire == null) {
								pstmt_tDBOutput_4.setNull(6, java.sql.Types.DOUBLE);
							} else {
								pstmt_tDBOutput_4.setDouble(6, CT_produit.prix_unitaire);
							}

							pstmt_tDBOutput_4.addBatch();
							nb_line_tDBOutput_4++;

							batchSizeCounter_tDBOutput_4++;
							if (batchSize_tDBOutput_4 <= batchSizeCounter_tDBOutput_4) {
								try {
									int countSum_tDBOutput_4 = 0;
									for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_4_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_4 = 0;
									for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
									System.err.println(e.getMessage());
								}

								batchSizeCounter_tDBOutput_4 = 0;
							}
							commitCounter_tDBOutput_4++;

							if (commitEvery_tDBOutput_4 <= commitCounter_tDBOutput_4) {

								try {
									int countSum_tDBOutput_4 = 0;
									for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_4_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_4 = 0;
									for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
										countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
									}
									rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
									insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
									System.err.println(e.getMessage());

								}
								if (rowsToCommitCount_tDBOutput_4 != 0) {
								}
								conn_tDBOutput_4.commit();
								if (rowsToCommitCount_tDBOutput_4 != 0) {
									rowsToCommitCount_tDBOutput_4 = 0;
								}
								commitCounter_tDBOutput_4 = 0;

							}

							tos_count_tDBOutput_4++;

							/**
							 * [tDBOutput_4 main ] stop
							 */

							/**
							 * [tDBOutput_4 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_4";

							/**
							 * [tDBOutput_4 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_4 process_data_end ] start
							 */

							currentComponent = "tDBOutput_4";

							/**
							 * [tDBOutput_4 process_data_end ] stop
							 */

						} // End of branch "CT_produit"

						/**
						 * [tMap_4 process_data_end ] start
						 */

						currentComponent = "tMap_4";

						/**
						 * [tMap_4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 process_data_end ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 end ] start
						 */

						currentComponent = "tDBInput_4";

					}
				} finally {
					if (rs_tDBInput_4 != null) {
						rs_tDBInput_4.close();
					}
					if (stmt_tDBInput_4 != null) {
						stmt_tDBInput_4.close();
					}
					if (conn_tDBInput_4 != null && !conn_tDBInput_4.isClosed()) {

						conn_tDBInput_4.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				globalMap.put("tDBInput_4_NB_LINE", nb_line_tDBInput_4);

				ok_Hash.put("tDBInput_4", true);
				end_Hash.put("tDBInput_4", System.currentTimeMillis());

				/**
				 * [tDBInput_4 end ] stop
				 */

				/**
				 * [tMap_4 end ] start
				 */

				currentComponent = "tMap_4";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row4");
				}

				ok_Hash.put("tMap_4", true);
				end_Hash.put("tMap_4", System.currentTimeMillis());

				/**
				 * [tMap_4 end ] stop
				 */

				/**
				 * [tDBOutput_4 end ] start
				 */

				currentComponent = "tDBOutput_4";

				try {
					if (batchSizeCounter_tDBOutput_4 != 0) {
						int countSum_tDBOutput_4 = 0;

						for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
							countSum_tDBOutput_4 += (countEach_tDBOutput_4 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

						insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

					}

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_4 = 0;
					for (int countEach_tDBOutput_4 : e.getUpdateCounts()) {
						countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
					}
					rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

					insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_4 = 0;

				if (pstmt_tDBOutput_4 != null) {

					pstmt_tDBOutput_4.close();
					resourceMap.remove("pstmt_tDBOutput_4");

				}
				resourceMap.put("statementClosed_tDBOutput_4", true);
				if (commitCounter_tDBOutput_4 > 0 && rowsToCommitCount_tDBOutput_4 != 0) {

				}
				conn_tDBOutput_4.commit();
				if (commitCounter_tDBOutput_4 > 0 && rowsToCommitCount_tDBOutput_4 != 0) {

					rowsToCommitCount_tDBOutput_4 = 0;
				}
				commitCounter_tDBOutput_4 = 0;

				conn_tDBOutput_4.close();

				resourceMap.put("finish_tDBOutput_4", true);

				nb_line_deleted_tDBOutput_4 = nb_line_deleted_tDBOutput_4 + deletedCount_tDBOutput_4;
				nb_line_update_tDBOutput_4 = nb_line_update_tDBOutput_4 + updatedCount_tDBOutput_4;
				nb_line_inserted_tDBOutput_4 = nb_line_inserted_tDBOutput_4 + insertedCount_tDBOutput_4;
				nb_line_rejected_tDBOutput_4 = nb_line_rejected_tDBOutput_4 + rejectedCount_tDBOutput_4;

				globalMap.put("tDBOutput_4_NB_LINE", nb_line_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_UPDATED", nb_line_update_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_DELETED", nb_line_deleted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_4);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "CT_produit");
				}

				ok_Hash.put("tDBOutput_4", true);
				end_Hash.put("tDBOutput_4", System.currentTimeMillis());

				/**
				 * [tDBOutput_4 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_4 finally ] start
				 */

				currentComponent = "tDBInput_4";

				/**
				 * [tDBInput_4 finally ] stop
				 */

				/**
				 * [tMap_4 finally ] start
				 */

				currentComponent = "tMap_4";

				/**
				 * [tMap_4 finally ] stop
				 */

				/**
				 * [tDBOutput_4 finally ] start
				 */

				currentComponent = "tDBOutput_4";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_4") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_4 = null;
						if ((pstmtToClose_tDBOutput_4 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_4")) != null) {
							pstmtToClose_tDBOutput_4.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_4") == null) {
						java.sql.Connection ctn_tDBOutput_4 = null;
						if ((ctn_tDBOutput_4 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_4")) != null) {
							try {
								ctn_tDBOutput_4.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_4) {
								String errorMessage_tDBOutput_4 = "failed to close the connection in tDBOutput_4 :"
										+ sqlEx_tDBOutput_4.getMessage();
								System.err.println(errorMessage_tDBOutput_4);
							}
						}
					}
				}

				/**
				 * [tDBOutput_4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 1);
	}

	public static class CT_sous_categorieStruct implements routines.system.IPersistableRow<CT_sous_categorieStruct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int id_sous_categorie;

		public int getId_sous_categorie() {
			return this.id_sous_categorie;
		}

		public String nom_sous_categorie;

		public String getNom_sous_categorie() {
			return this.nom_sous_categorie;
		}

		public Integer id_categorie;

		public Integer getId_categorie() {
			return this.id_categorie;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.id_sous_categorie;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final CT_sous_categorieStruct other = (CT_sous_categorieStruct) obj;

			if (this.id_sous_categorie != other.id_sous_categorie)
				return false;

			return true;
		}

		public void copyDataTo(CT_sous_categorieStruct other) {

			other.id_sous_categorie = this.id_sous_categorie;
			other.nom_sous_categorie = this.nom_sous_categorie;
			other.id_categorie = this.id_categorie;

		}

		public void copyKeysDataTo(CT_sous_categorieStruct other) {

			other.id_sous_categorie = this.id_sous_categorie;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_sous_categorie = dis.readInt();

					this.nom_sous_categorie = readString(dis);

					this.id_categorie = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_sous_categorie = dis.readInt();

					this.nom_sous_categorie = readString(dis);

					this.id_categorie = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.id_sous_categorie);

				// String

				writeString(this.nom_sous_categorie, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.id_sous_categorie);

				// String

				writeString(this.nom_sous_categorie, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_sous_categorie=" + String.valueOf(id_sous_categorie));
			sb.append(",nom_sous_categorie=" + nom_sous_categorie);
			sb.append(",id_categorie=" + String.valueOf(id_categorie));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(CT_sous_categorieStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id_sous_categorie, other.id_sous_categorie);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];
		static byte[] commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[0];

		public Integer id_sous_categorie;

		public Integer getId_sous_categorie() {
			return this.id_sous_categorie;
		}

		public String nom_sous_categorie;

		public String getNom_sous_categorie() {
			return this.nom_sous_categorie;
		}

		public String description;

		public String getDescription() {
			return this.description;
		}

		public Integer id_categorie;

		public Integer getId_categorie() {
			return this.id_categorie;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length) {
					if (length < 1024 && commonByteArray_DATAWAREHOUSE_PROJECT_Transformation.length == 0) {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[1024];
					} else {
						commonByteArray_DATAWAREHOUSE_PROJECT_Transformation = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length);
				strReturn = new String(commonByteArray_DATAWAREHOUSE_PROJECT_Transformation, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_sous_categorie = readInteger(dis);

					this.nom_sous_categorie = readString(dis);

					this.description = readString(dis);

					this.id_categorie = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_DATAWAREHOUSE_PROJECT_Transformation) {

				try {

					int length = 0;

					this.id_sous_categorie = readInteger(dis);

					this.nom_sous_categorie = readString(dis);

					this.description = readString(dis);

					this.id_categorie = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id_sous_categorie, dos);

				// String

				writeString(this.nom_sous_categorie, dos);

				// String

				writeString(this.description, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id_sous_categorie, dos);

				// String

				writeString(this.nom_sous_categorie, dos);

				// String

				writeString(this.description, dos);

				// Integer

				writeInteger(this.id_categorie, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id_sous_categorie=" + String.valueOf(id_sous_categorie));
			sb.append(",nom_sous_categorie=" + nom_sous_categorie);
			sb.append(",description=" + description);
			sb.append(",id_categorie=" + String.valueOf(id_categorie));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row5Struct row5 = new row5Struct();
				CT_sous_categorieStruct CT_sous_categorie = new CT_sous_categorieStruct();

				/**
				 * [tDBOutput_5 begin ] start
				 */

				ok_Hash.put("tDBOutput_5", false);
				start_Hash.put("tDBOutput_5", System.currentTimeMillis());

				currentComponent = "tDBOutput_5";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "CT_sous_categorie");
				}

				int tos_count_tDBOutput_5 = 0;

				int nb_line_tDBOutput_5 = 0;
				int nb_line_update_tDBOutput_5 = 0;
				int nb_line_inserted_tDBOutput_5 = 0;
				int nb_line_deleted_tDBOutput_5 = 0;
				int nb_line_rejected_tDBOutput_5 = 0;

				int deletedCount_tDBOutput_5 = 0;
				int updatedCount_tDBOutput_5 = 0;
				int insertedCount_tDBOutput_5 = 0;
				int rowsToCommitCount_tDBOutput_5 = 0;
				int rejectedCount_tDBOutput_5 = 0;

				String tableName_tDBOutput_5 = "Dim_sous_categorie";
				boolean whetherReject_tDBOutput_5 = false;

				java.util.Calendar calendar_tDBOutput_5 = java.util.Calendar.getInstance();
				calendar_tDBOutput_5.set(1, 0, 1, 0, 0, 0);
				long year1_tDBOutput_5 = calendar_tDBOutput_5.getTime().getTime();
				calendar_tDBOutput_5.set(10000, 0, 1, 0, 0, 0);
				long year10000_tDBOutput_5 = calendar_tDBOutput_5.getTime().getTime();
				long date_tDBOutput_5;

				java.sql.Connection conn_tDBOutput_5 = null;

				String properties_tDBOutput_5 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBOutput_5 == null || properties_tDBOutput_5.trim().length() == 0) {
					properties_tDBOutput_5 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBOutput_5.contains("rewriteBatchedStatements=")) {
						properties_tDBOutput_5 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBOutput_5.contains("allowLoadLocalInfile=")) {
						properties_tDBOutput_5 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBOutput_5 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "olap" + "?"
						+ properties_tDBOutput_5;

				String driverClass_tDBOutput_5 = "com.mysql.cj.jdbc.Driver";

				String dbUser_tDBOutput_5 = "root";

				final String decryptedPassword_tDBOutput_5 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:DN5fDLexEbuV7D1yaA80f8818HvG0XFiFdRpaw==");

				String dbPwd_tDBOutput_5 = decryptedPassword_tDBOutput_5;
				java.lang.Class.forName(driverClass_tDBOutput_5);

				conn_tDBOutput_5 = java.sql.DriverManager.getConnection(url_tDBOutput_5, dbUser_tDBOutput_5,
						dbPwd_tDBOutput_5);

				resourceMap.put("conn_tDBOutput_5", conn_tDBOutput_5);
				conn_tDBOutput_5.setAutoCommit(false);
				int commitEvery_tDBOutput_5 = 10000;
				int commitCounter_tDBOutput_5 = 0;

				int count_tDBOutput_5 = 0;

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_5 = conn_tDBOutput_5.getMetaData();
				java.sql.ResultSet rsTable_tDBOutput_5 = dbMetaData_tDBOutput_5.getTables("olap", null, null,
						new String[] { "TABLE" });
				boolean whetherExist_tDBOutput_5 = false;
				while (rsTable_tDBOutput_5.next()) {
					String table_tDBOutput_5 = rsTable_tDBOutput_5.getString("TABLE_NAME");
					if (table_tDBOutput_5.equalsIgnoreCase("Dim_sous_categorie")) {
						whetherExist_tDBOutput_5 = true;
						break;
					}
				}
				if (whetherExist_tDBOutput_5) {
					try (java.sql.Statement stmtDrop_tDBOutput_5 = conn_tDBOutput_5.createStatement()) {
						stmtDrop_tDBOutput_5.execute("DROP TABLE `" + tableName_tDBOutput_5 + "`");
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_5 = conn_tDBOutput_5.createStatement()) {
					stmtCreate_tDBOutput_5.execute("CREATE TABLE `" + tableName_tDBOutput_5
							+ "`(`id_sous_categorie` VARCHAR(200)   not null ,`nom_sous_categorie` VARCHAR(200)  ,`id_categorie` VARCHAR(200)  ,primary key(`id_sous_categorie`))");
				}

				String insert_tDBOutput_5 = "INSERT INTO `" + "Dim_sous_categorie"
						+ "` (`id_sous_categorie`,`nom_sous_categorie`,`id_categorie`) VALUES (?,?,?)";
				int batchSize_tDBOutput_5 = 100;
				int batchSizeCounter_tDBOutput_5 = 0;

				java.sql.PreparedStatement pstmt_tDBOutput_5 = conn_tDBOutput_5.prepareStatement(insert_tDBOutput_5);
				resourceMap.put("pstmt_tDBOutput_5", pstmt_tDBOutput_5);

				/**
				 * [tDBOutput_5 begin ] stop
				 */

				/**
				 * [tMap_5 begin ] start
				 */

				ok_Hash.put("tMap_5", false);
				start_Hash.put("tMap_5", System.currentTimeMillis());

				currentComponent = "tMap_5";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row5");
				}

				int tos_count_tMap_5 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_5__Struct {
				}
				Var__tMap_5__Struct Var__tMap_5 = new Var__tMap_5__Struct();
// ###############################

// ###############################
// # Outputs initialization
				CT_sous_categorieStruct CT_sous_categorie_tmp = new CT_sous_categorieStruct();
// ###############################

				/**
				 * [tMap_5 begin ] stop
				 */

				/**
				 * [tDBInput_5 begin ] start
				 */

				ok_Hash.put("tDBInput_5", false);
				start_Hash.put("tDBInput_5", System.currentTimeMillis());

				currentComponent = "tDBInput_5";

				int tos_count_tDBInput_5 = 0;

				java.util.Calendar calendar_tDBInput_5 = java.util.Calendar.getInstance();
				calendar_tDBInput_5.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_5 = calendar_tDBInput_5.getTime();
				int nb_line_tDBInput_5 = 0;
				java.sql.Connection conn_tDBInput_5 = null;
				String driverClass_tDBInput_5 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_5 = java.lang.Class.forName(driverClass_tDBInput_5);
				String dbUser_tDBInput_5 = "root";

				final String decryptedPassword_tDBInput_5 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:DFLNaXj6nn9iEb5WyA1Gp5qhBSc1oMqo+OGd7Q==");

				String dbPwd_tDBInput_5 = decryptedPassword_tDBInput_5;

				String properties_tDBInput_5 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_5 == null || properties_tDBInput_5.trim().length() == 0) {
					properties_tDBInput_5 = "";
				}
				String url_tDBInput_5 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "datawarehouse" + "?"
						+ properties_tDBInput_5;

				conn_tDBInput_5 = java.sql.DriverManager.getConnection(url_tDBInput_5, dbUser_tDBInput_5,
						dbPwd_tDBInput_5);

				java.sql.Statement stmt_tDBInput_5 = conn_tDBInput_5.createStatement();

				String dbquery_tDBInput_5 = "SELECT \n  `sous_categories`.`id_sous_categorie`, \n  `sous_categories`.`nom_sous_categorie`, \n  `sous_categories`.`descr"
						+ "iption`, \n  `sous_categories`.`id_categorie`\nFROM `sous_categories`";

				globalMap.put("tDBInput_5_QUERY", dbquery_tDBInput_5);
				java.sql.ResultSet rs_tDBInput_5 = null;

				try {
					rs_tDBInput_5 = stmt_tDBInput_5.executeQuery(dbquery_tDBInput_5);
					java.sql.ResultSetMetaData rsmd_tDBInput_5 = rs_tDBInput_5.getMetaData();
					int colQtyInRs_tDBInput_5 = rsmd_tDBInput_5.getColumnCount();

					String tmpContent_tDBInput_5 = null;

					while (rs_tDBInput_5.next()) {
						nb_line_tDBInput_5++;

						if (colQtyInRs_tDBInput_5 < 1) {
							row5.id_sous_categorie = null;
						} else {

							row5.id_sous_categorie = rs_tDBInput_5.getInt(1);
							if (rs_tDBInput_5.wasNull()) {
								row5.id_sous_categorie = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 2) {
							row5.nom_sous_categorie = null;
						} else {

							row5.nom_sous_categorie = routines.system.JDBCUtil.getString(rs_tDBInput_5, 2, false);
						}
						if (colQtyInRs_tDBInput_5 < 3) {
							row5.description = null;
						} else {

							row5.description = routines.system.JDBCUtil.getString(rs_tDBInput_5, 3, false);
						}
						if (colQtyInRs_tDBInput_5 < 4) {
							row5.id_categorie = null;
						} else {

							row5.id_categorie = rs_tDBInput_5.getInt(4);
							if (rs_tDBInput_5.wasNull()) {
								row5.id_categorie = null;
							}
						}

						/**
						 * [tDBInput_5 begin ] stop
						 */

						/**
						 * [tDBInput_5 main ] start
						 */

						currentComponent = "tDBInput_5";

						tos_count_tDBInput_5++;

						/**
						 * [tDBInput_5 main ] stop
						 */

						/**
						 * [tDBInput_5 process_data_begin ] start
						 */

						currentComponent = "tDBInput_5";

						/**
						 * [tDBInput_5 process_data_begin ] stop
						 */

						/**
						 * [tMap_5 main ] start
						 */

						currentComponent = "tMap_5";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row5"

							);
						}

						boolean hasCasePrimitiveKeyWithNull_tMap_5 = false;

						// ###############################
						// # Input tables (lookups)
						boolean rejectedInnerJoin_tMap_5 = false;
						boolean mainRowRejected_tMap_5 = false;

						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_5__Struct Var = Var__tMap_5;// ###############################
							// ###############################
							// # Output tables

							CT_sous_categorie = null;

// # Output table : 'CT_sous_categorie'
							CT_sous_categorie_tmp.id_sous_categorie = row5.id_sous_categorie;
							CT_sous_categorie_tmp.nom_sous_categorie = row5.nom_sous_categorie;
							CT_sous_categorie_tmp.id_categorie = row5.id_categorie;
							CT_sous_categorie = CT_sous_categorie_tmp;
// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_5 = false;

						tos_count_tMap_5++;

						/**
						 * [tMap_5 main ] stop
						 */

						/**
						 * [tMap_5 process_data_begin ] start
						 */

						currentComponent = "tMap_5";

						/**
						 * [tMap_5 process_data_begin ] stop
						 */
// Start of branch "CT_sous_categorie"
						if (CT_sous_categorie != null) {

							/**
							 * [tDBOutput_5 main ] start
							 */

							currentComponent = "tDBOutput_5";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "CT_sous_categorie"

								);
							}

							whetherReject_tDBOutput_5 = false;
							pstmt_tDBOutput_5.setInt(1, CT_sous_categorie.id_sous_categorie);

							if (CT_sous_categorie.nom_sous_categorie == null) {
								pstmt_tDBOutput_5.setNull(2, java.sql.Types.VARCHAR);
							} else {
								pstmt_tDBOutput_5.setString(2, CT_sous_categorie.nom_sous_categorie);
							}

							if (CT_sous_categorie.id_categorie == null) {
								pstmt_tDBOutput_5.setNull(3, java.sql.Types.INTEGER);
							} else {
								pstmt_tDBOutput_5.setInt(3, CT_sous_categorie.id_categorie);
							}

							pstmt_tDBOutput_5.addBatch();
							nb_line_tDBOutput_5++;

							batchSizeCounter_tDBOutput_5++;
							if (batchSize_tDBOutput_5 <= batchSizeCounter_tDBOutput_5) {
								try {
									int countSum_tDBOutput_5 = 0;
									for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
										countSum_tDBOutput_5 += (countEach_tDBOutput_5 == java.sql.Statement.EXECUTE_FAILED
												? 0
												: 1);
									}
									rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;
									insertedCount_tDBOutput_5 += countSum_tDBOutput_5;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_5_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_5 = 0;
									for (int countEach_tDBOutput_5 : e.getUpdateCounts()) {
										countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0 : countEach_tDBOutput_5);
									}
									rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;
									insertedCount_tDBOutput_5 += countSum_tDBOutput_5;
									System.err.println(e.getMessage());
								}

								batchSizeCounter_tDBOutput_5 = 0;
							}
							commitCounter_tDBOutput_5++;

							if (commitEvery_tDBOutput_5 <= commitCounter_tDBOutput_5) {

								try {
									int countSum_tDBOutput_5 = 0;
									for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
										countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0 : 1);
									}
									rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;
									insertedCount_tDBOutput_5 += countSum_tDBOutput_5;
								} catch (java.sql.BatchUpdateException e) {
									globalMap.put("tDBOutput_5_ERROR_MESSAGE", e.getMessage());
									int countSum_tDBOutput_5 = 0;
									for (int countEach_tDBOutput_5 : e.getUpdateCounts()) {
										countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0 : countEach_tDBOutput_5);
									}
									rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;
									insertedCount_tDBOutput_5 += countSum_tDBOutput_5;
									System.err.println(e.getMessage());

								}
								if (rowsToCommitCount_tDBOutput_5 != 0) {
								}
								conn_tDBOutput_5.commit();
								if (rowsToCommitCount_tDBOutput_5 != 0) {
									rowsToCommitCount_tDBOutput_5 = 0;
								}
								commitCounter_tDBOutput_5 = 0;

							}

							tos_count_tDBOutput_5++;

							/**
							 * [tDBOutput_5 main ] stop
							 */

							/**
							 * [tDBOutput_5 process_data_begin ] start
							 */

							currentComponent = "tDBOutput_5";

							/**
							 * [tDBOutput_5 process_data_begin ] stop
							 */

							/**
							 * [tDBOutput_5 process_data_end ] start
							 */

							currentComponent = "tDBOutput_5";

							/**
							 * [tDBOutput_5 process_data_end ] stop
							 */

						} // End of branch "CT_sous_categorie"

						/**
						 * [tMap_5 process_data_end ] start
						 */

						currentComponent = "tMap_5";

						/**
						 * [tMap_5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 process_data_end ] start
						 */

						currentComponent = "tDBInput_5";

						/**
						 * [tDBInput_5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 end ] start
						 */

						currentComponent = "tDBInput_5";

					}
				} finally {
					if (rs_tDBInput_5 != null) {
						rs_tDBInput_5.close();
					}
					if (stmt_tDBInput_5 != null) {
						stmt_tDBInput_5.close();
					}
					if (conn_tDBInput_5 != null && !conn_tDBInput_5.isClosed()) {

						conn_tDBInput_5.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				globalMap.put("tDBInput_5_NB_LINE", nb_line_tDBInput_5);

				ok_Hash.put("tDBInput_5", true);
				end_Hash.put("tDBInput_5", System.currentTimeMillis());

				/**
				 * [tDBInput_5 end ] stop
				 */

				/**
				 * [tMap_5 end ] start
				 */

				currentComponent = "tMap_5";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row5");
				}

				ok_Hash.put("tMap_5", true);
				end_Hash.put("tMap_5", System.currentTimeMillis());

				/**
				 * [tMap_5 end ] stop
				 */

				/**
				 * [tDBOutput_5 end ] start
				 */

				currentComponent = "tDBOutput_5";

				try {
					if (batchSizeCounter_tDBOutput_5 != 0) {
						int countSum_tDBOutput_5 = 0;

						for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
							countSum_tDBOutput_5 += (countEach_tDBOutput_5 == java.sql.Statement.EXECUTE_FAILED ? 0
									: 1);
						}
						rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

						insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

					}

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_5 = 0;
					for (int countEach_tDBOutput_5 : e.getUpdateCounts()) {
						countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0 : countEach_tDBOutput_5);
					}
					rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

					insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

					System.err.println(e.getMessage());

				}
				batchSizeCounter_tDBOutput_5 = 0;

				if (pstmt_tDBOutput_5 != null) {

					pstmt_tDBOutput_5.close();
					resourceMap.remove("pstmt_tDBOutput_5");

				}
				resourceMap.put("statementClosed_tDBOutput_5", true);
				if (commitCounter_tDBOutput_5 > 0 && rowsToCommitCount_tDBOutput_5 != 0) {

				}
				conn_tDBOutput_5.commit();
				if (commitCounter_tDBOutput_5 > 0 && rowsToCommitCount_tDBOutput_5 != 0) {

					rowsToCommitCount_tDBOutput_5 = 0;
				}
				commitCounter_tDBOutput_5 = 0;

				conn_tDBOutput_5.close();

				resourceMap.put("finish_tDBOutput_5", true);

				nb_line_deleted_tDBOutput_5 = nb_line_deleted_tDBOutput_5 + deletedCount_tDBOutput_5;
				nb_line_update_tDBOutput_5 = nb_line_update_tDBOutput_5 + updatedCount_tDBOutput_5;
				nb_line_inserted_tDBOutput_5 = nb_line_inserted_tDBOutput_5 + insertedCount_tDBOutput_5;
				nb_line_rejected_tDBOutput_5 = nb_line_rejected_tDBOutput_5 + rejectedCount_tDBOutput_5;

				globalMap.put("tDBOutput_5_NB_LINE", nb_line_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_UPDATED", nb_line_update_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_DELETED", nb_line_deleted_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_5);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "CT_sous_categorie");
				}

				ok_Hash.put("tDBOutput_5", true);
				end_Hash.put("tDBOutput_5", System.currentTimeMillis());

				/**
				 * [tDBOutput_5 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_5 finally ] start
				 */

				currentComponent = "tDBInput_5";

				/**
				 * [tDBInput_5 finally ] stop
				 */

				/**
				 * [tMap_5 finally ] start
				 */

				currentComponent = "tMap_5";

				/**
				 * [tMap_5 finally ] stop
				 */

				/**
				 * [tDBOutput_5 finally ] start
				 */

				currentComponent = "tDBOutput_5";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_5") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_5 = null;
						if ((pstmtToClose_tDBOutput_5 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_5")) != null) {
							pstmtToClose_tDBOutput_5.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_5") == null) {
						java.sql.Connection ctn_tDBOutput_5 = null;
						if ((ctn_tDBOutput_5 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_5")) != null) {
							try {
								ctn_tDBOutput_5.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_5) {
								String errorMessage_tDBOutput_5 = "failed to close the connection in tDBOutput_5 :"
										+ sqlEx_tDBOutput_5.getMessage();
								System.err.println(errorMessage_tDBOutput_5);
							}
						}
					}
				}

				/**
				 * [tDBOutput_5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final Transformation TransformationClass = new Transformation();

		int exitCode = TransformationClass.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = Transformation.class.getClassLoader().getResourceAsStream(
					"datawarehouse_project/transformation_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = Transformation.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tDBInput_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_1) {
			globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

			e_tDBInput_1.printStackTrace();

		}
		try {
			errorCode = null;
			tDBInput_2Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_2) {
			globalMap.put("tDBInput_2_SUBPROCESS_STATE", -1);

			e_tDBInput_2.printStackTrace();

		}
		try {
			errorCode = null;
			tDBInput_3Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_3) {
			globalMap.put("tDBInput_3_SUBPROCESS_STATE", -1);

			e_tDBInput_3.printStackTrace();

		}
		try {
			errorCode = null;
			tDBInput_4Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_4) {
			globalMap.put("tDBInput_4_SUBPROCESS_STATE", -1);

			e_tDBInput_4.printStackTrace();

		}
		try {
			errorCode = null;
			tDBInput_5Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_5) {
			globalMap.put("tDBInput_5_SUBPROCESS_STATE", -1);

			e_tDBInput_5.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println(
					(endUsedMemory - startUsedMemory) + " bytes memory increase when running : Transformation");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 231509 characters generated by Talend Open Studio for Data Integration on the
 * 12 mai 2025 à 23:18:02 WEST
 ************************************************************************************************/