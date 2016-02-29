/*
# Licensed Materials - Property of IBM
# Copyright IBM Corp. 2015, 2016 
*/
package quarks.execution.services;

/**
 * Service that provides a control mechanism.
 * <BR>
 * The control service allows applications and Quarks itself to
 * register control interfaces generically. The style of a control interface
 * is similar to a JMX Management Bean (MBean), specifically a JMX MXBean.
 * <BR>
 * No dependency is created on any JMX interface to allow running on systems
 * that do not support JMX, such as Android.
 * <P>
 * Different implementations of the control service provide the mechanism
 * to execute methods of the control interfaces. For example JMX control
 * registers the MBeans in the platform JMXserver.
 * <BR>
 * The control service is intended to allow remote execution of a control interface
 * through any mechanism. The control service provides operations and attributes
 * similar to JMX. It does not provide notifications.
 * </P>
 * <P>
 * An instance of a control service MBean is defined by its:
 * 
 * <UL>
 * <LI> A type </LI>
 * <LI> A identifier - Unique within the current execution context.</LI>
 * <LI> An alias - Optional, but can be combined with the control MBeans's type 
 * to logically identify a control MBean. </LI>
 * <LI> A Java interface - This defines what operations can be executed
 * against the control MBean.</LI>
 * </UL>
 * A remote system should be able to specify an operation on an
 * control server MBean though its alias and type. For example
 * an application might be submitted with a fixed name
 * <em>PumpAnalytics</em> (as its alias)
 * to allow its {@link quarks.execution.mbeans.JobMXBean JobMXBean}
 * to be determined remotely using a combination of
 * {@link quarks.execution.mbeans.JobMXBean#TYPE JobMXBean.TYPE}
 * and <em>PumpAnalytics</em>.
 * </P>
 * <P>
 * Control service implementations may be limited in their capabilities,
 * for example when using the JMX control service the full capabilities
 * of JMX can be used, such as complex types in a control service MBean interface.
 * Portable applications would limit themselves to a smaller subset of
 * capabilities, such as only primitive types and enums.
 * <BR>
 * The method {@link Controls#isControlServiceMBean(Class)} defines
 * the minimal supported interface for any control service.
 * </P>
 */
public interface ControlService {

    /**
     * Register a control server MBean for an oplet.
     * 
     * @param type Type of the control object.
     * @param id
     *            Unique identifier for the control object.
     * @param alias
     *            Alias for the control object. Expected to be unique within the context
     *            of {@code type}.
     * @param controlInterface
     *            Public interface for the control object.
     * @param control
     *            The control bean
     * @return unique identifier that can be used to unregister an control mbean.
     */
    <T> String registerControl(String type, String id, String alias, Class<T> controlInterface, T control);
    
    /**
     * Unregister a control bean registered by {@link #registerControl(String, String, String, Class, Object)}
     */
    void unregister(String controlId);
}
