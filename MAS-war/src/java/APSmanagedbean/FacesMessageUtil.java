/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;



import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * Utility class to help JSF developers create <code>FacesMessage</code> passing normal String, or retrieve values from UIResources.properties file.
 * The created <code>FacesMessage</code> can be added to a desired <code>UIComponent</code>, or the current <code>FacesContext</code>.
 */
/**
 *
 * @author Xi
 */
public class FacesMessageUtil {

	private static final String UI_BUNDLE = "tdb.view.bundles.ui.UIResources";
	
    private static ResourceBundle uiBundle = null;

    private FacesMessageUtil() {
        // Utility class, hide constructor.
    }

    /**
     * Returns a string value from the <code>UIResources.properties</code> bundle associated with the given key,
     * localized, and formatted with the given parameters, if any.
     * @param key The string key in the loaded resource bundle.
     * @param locale Defines the localization file.
     * @param params Optional parameters to format the string using <code>MessageFormat</code>.
     * @return The string value from the resource bundle associated with the given key, formatted
     * with the given parameters, if any.
     * @see FacesMessageUtil#getString(String, ResourceBundle)
     */
    public static String getString(String key, Locale locale, Object... params) {
    	try {
    		if(uiBundle == null)
                uiBundle = ResourceBundle.getBundle(UI_BUNDLE, locale);
    	} catch(MissingResourceException e) {
    		return "'"+ UI_BUNDLE + "' Missing resource.";
    	}
    	
        return getString(key, uiBundle, params);
    }

    /**
     * Retrieve a defined string from the given bundle file,
     * according to the passed key.
     *
     * @param key The string key in the loaded resource bundle.
     * @param bundle The <code>ResourceBundle</code> file where the key is located.
     * @param params Optional parameters to format the message using <code>MessageFormat</code>.
     * @return The string value from the given bundle file matching the given key.
     */
    public static String getString(String key, ResourceBundle bundle, Object...params) {
    	if (key==null || key.isEmpty())
    		return "";
    	try {
    		return MessageFormat.format(bundle.getString(key), params);
    	} catch(MissingResourceException e) {
    		return "?? "+key+" ?? Key not found.";
    	}
    }
    
    /**
     * Add a <code>FacesMessage</code> from the loaded <code>ResourceBundle</code> file, to the current <code>FacesContext</code>, with a defined severity.
     *
     * @param severity <code>FacesMessage.Severity</code> defines the <code>FacesMessage</code> status of four options,
     * <code>FacesMessage.SEVERITY_INFO</code>, <code>FacesMessage.SEVERITY_WARN</code>, 
     * <code>FacesMessage.SEVERITY_ERROR</code>, <code>FacesMessage.SEVERITY_FATAL</code>.
     * @param summaryKey The summary message key in the loaded resource bundle.
     * @param detailKey The detail message key in the loaded resource bundle.
     * @param params Optional parameters to format the message using <code>MessageFormat</code>.
     * @see FacesMessageUtil#addBundledFacesMessage(UIComponent, FacesMessage.Severity, String, String, Object[])
     */
    public static void addContextBundledFacesMessage(FacesMessage.Severity severity, String summaryKey, String detailKey, Object...params) {
    	addBundledFacesMessage(null, severity, summaryKey, detailKey, params);
    }
    
    /**
     * Add a <code>FacesMessage</code>, to the current <code>FacesContext</code>, with a defined severity.
     *
     * @param severity <code>FacesMessage.Severity</code> defines the <code>FacesMessage</code> status of four options,
     * <code>FacesMessage.SEVERITY_INFO</code>, <code>FacesMessage.SEVERITY_WARN</code>, 
     * <code>FacesMessage.SEVERITY_ERROR</code>, <code>FacesMessage.SEVERITY_FATAL</code>.
     * @param summary The summary message.
     * @param detail The detail message.
     * @see FacesMessageUtil#addFacesMessage(UIComponent, FacesMessage.Severity, String, String)
     */
    public static void addContextFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
    	addFacesMessage(null, severity, summary, detail);
    }
    
    /**
     * Add a <code>FacesMessage</code> from the loaded <code>ResourceBundle</code> file, to the given <code>UIComponent</code>, with a defined severity.
     * 
     * @param component The <code>UIComponent</code> to associate the created <code>FacesMessage</code> with.
     * @param severity <code>FacesMessage.Severity</code> defines the <code>FacesMessage</code> status of four options,
     * <code>FacesMessage.SEVERITY_INFO</code>, <code>FacesMessage.SEVERITY_WARN</code>, 
     * <code>FacesMessage.SEVERITY_ERROR</code>, <code>FacesMessage.SEVERITY_FATAL</code>.
     * @param summaryKey The summary message key in the loaded resource bundle.
     * @param detailKey The detail message key in the loaded resource bundle.
     * @param params Optional parameters to format the message using <code>MessageFormat</code>.
     * @see FacesMessageUtil#newBundledFacesMessage(FacesMessage.Severity, String, String, Object[])
     */
    public static void addBundledFacesMessage(UIComponent component, FacesMessage.Severity severity, String summaryKey, String detailKey, Object...params) {
    	FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component!=null ? component.getClientId(context) : null, newBundledFacesMessage(severity, summaryKey, detailKey, params));
    }
    
    /**
     * Add a <code>FacesMessage</code>, to the given <code>UIComponent</code>, with a defined severity.
     *
     * @param component The <code>UIComponent</code> to associate the created <code>FacesMessage</code> with.
     * @param severity <code>FacesMessage.Severity</code> defines the <code>FacesMessage</code> status of four options,
     * <code>FacesMessage.SEVERITY_INFO</code>, <code>FacesMessage.SEVERITY_WARN</code>, 
     * <code>FacesMessage.SEVERITY_ERROR</code>, <code>FacesMessage.SEVERITY_FATAL</code>.
     * @param summary The summary message.
     * @param detail The detail message.
     * @see FacesMessageUtil#newFacesMessage(FacesMessage.Severity, String, String)
     */
    public static void addFacesMessage(UIComponent component, FacesMessage.Severity severity, String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component!=null ? component.getClientId(context) : null, newFacesMessage(severity, summary, detail));
    }
    
    /**
     * Get a new <code>FacesMessage</code> object with the given summary, detail, and the passed severity status.
     *
     * @param severity <code>FacesMessage.Severity</code> defines the <code>FacesMessage</code> status of four options,
     * <code>FacesMessage.SEVERITY_INFO</code>, <code>FacesMessage.SEVERITY_WARN</code>, 
     * <code>FacesMessage.SEVERITY_ERROR</code>, <code>FacesMessage.SEVERITY_FATAL</code>.
     * @param summary The summary message.
     * @param detail The detail message.
     * @return <code>FacesMessage</code> object.
     */
    public static FacesMessage newFacesMessage(Severity severity, String summary, String detail) {
    	return new FacesMessage(severity, summary, detail);
    }
    
    /**
     * Get a new <code>FacesMessage</code> object, from the loaded <code>ResourceBundle</code> file, with the given summary, detail, and the passed severity status.
     *
     * @param severity <code>FacesMessage.Severity</code> defines the <code>FacesMessage</code> status of four options,
     * <code>FacesMessage.SEVERITY_INFO</code>, <code>FacesMessage.SEVERITY_WARN</code>, 
     * <code>FacesMessage.SEVERITY_ERROR</code>, <code>FacesMessage.SEVERITY_FATAL</code>.
     * @param summaryKey The summary message key in the loaded resource bundle.
     * @param detailKey The detail message key in the loaded resource bundle.
     * @param params Optional parameters to format the message using <code>MessageFormat</code>.
     * @return <code>FacesMessage</code> object.
     */
    public static FacesMessage newBundledFacesMessage(Severity severity, String summaryKey, String detailKey, Object...params) {
    	return new FacesMessage(severity, getString(summaryKey, getLocale()), getString(detailKey, getLocale(), params));
    }
    
    /**
     * Retrieve the set <code>Locale</code> in the <code>UIViewRoot</code> of the current <code>FacesContext</code>.
     *
     * @param context The current <code>FacesContext</code> object to reach the JSF pages <code>UIViewRoot</code>.
     * @return Current set <code>Locale</code> in <code>UIViewRoot</code>.
     */
    public static Locale getLocale() {
        Locale locale = null;
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        if (viewRoot != null)
            locale = viewRoot.getLocale();
        if (locale == null)
            locale = Locale.getDefault();
        return locale;
    }
    
    /**
     * Set the Locale in the <code>UIViewRoot</code> of the current <code>FacesContext</code>.
     *
     * @param locale The required <code>Locale</code> value to set in the <code>UIViewRoot</code>.
     */
    public static void setLocale(Locale locale) {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}