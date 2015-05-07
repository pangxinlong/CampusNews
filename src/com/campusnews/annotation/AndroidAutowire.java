package com.campusnews.annotation;

import java.lang.reflect.Field;

import android.app.Activity;
import android.view.View;


public class AndroidAutowire {

	  /**
	   * Perform the wiring of the Android View using the {@link AndroidView} annotation. <br />
	   * <br />
	   * <strong>Usage:</strong><br />
	   * <br />
	   * Annotation all view fields in the activity to be autowired. Use {@code @AndroidView}.<br />
	   * If you do not specify the {@code id} or the {@code resId} parameters in the annotation, the
	   * name of the variable will be used as the id. <br />
	   * You may specify whether or not the field is required (true by default). <br />
	   * <br />
	   * After the call to {@code setContentView(layoutResID)} in the onCreate() method, you will call
	   * this {@code autowire(Activity thisClass, Class<?> baseClass)} method. <br />
	   * The first parameter is the Activity class being loaded. <br />
	   * The second parameter is the class of the base activity (if applicable).
	   * 
	   * @param rootView The root view.
	   * @param baseClass The Base activity. If there is inheritance in the activities, this is the
	   *        highest level, the base activity, but not {@link Activity}. <br />
	   * <br />
	   *        All views annotated with {@code @AndroidView} will be autowired in all Activity classes
	   *        in the inheritance structure, from thisClass to baseClass inclusive. baseClass should
	   *        not be {@link Activity} because no fields in {@link Activity} will need to be autowired. <br />
	   * <br />
	   *        If there is no parent class for your activity, use thisClass.class as baseClass.
	   * @throws AndroidAutowireException Indicates that there was an issue autowiring a view to an
	   *         annotated field. Will not be thrown if required=false on the {@link AndroidView}
	   *         annotation.
	   */
	  public static void autowire(View rootView, Object baseClass) throws AndroidAutowireException {
	    Class<?> clazz = baseClass.getClass();
	    autowireViewsForClass(clazz, rootView, baseClass);
	    // Do this for all classes in the inheritance chain, until we get to this class
	    while (clazz.getSuperclass() != null) { // Object的超类为null
	      clazz = clazz.getSuperclass();
	      autowireViewsForClass(clazz, rootView, baseClass);
	    }
	  }

	  /**
	   * 在指定的超类superClazz中搜索AndroidView标记的field，从rootView中查找view，赋值给targetObj对象的field
	   * 
	   * @param superClazz Class<?> 指定的超类
	   * @param rootView View 指定的View容器
	   * @param targetObj Object 目标对象
	   */
	  private static void autowireViewsForClass(Class<?> superClazz, View rootView, Object targetObj) {
	    for (Field field : superClazz.getDeclaredFields()) {
	      // filter by AndroidView
	      if (!field.isAnnotationPresent(AndroidView.class)) {
	        continue;
	      }
	      if (!View.class.isAssignableFrom(field.getType())) {
	        continue;
	      }
	      AndroidView androidView = field.getAnnotation(AndroidView.class);
	      int resId = androidView.value();

	      try {
	        View view = rootView.findViewById(resId);
	        if (view != null) {
	          // throw new AndroidAutowireException("No view resource with the id of " + resId
	          // + " found. " + " The required field " + field.getName() + " could not be autowired");
	          field.setAccessible(true);
	          field.set(targetObj, view);
	        }
	      } catch (Exception e) {
	        throw new AndroidAutowireException("Cound not Autowire AndroidView: " + field.getName()
	            + ". " + e.getMessage());
	      }
	    }
	  }


}
