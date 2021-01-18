package com.hanu.draftapp.annotations;

/**
 * Annotate a RESTful servlet to represent a resource
 */
public @interface Resource {
    /**
     * Name of the resource
     */
    String name() default "";

    /**
     * The class of the primary resource of this servlet
     */
    Class<?> resourceClass() default Object.class;

    /**
     * <pre>
     *  Name of nested resources (resources that are contained) within the resource annotated by this.
     *  The nested resources can be used as follows: /resource/nestedResource
     * </pre>
     */
    String[] nestedResources() default {};
}
