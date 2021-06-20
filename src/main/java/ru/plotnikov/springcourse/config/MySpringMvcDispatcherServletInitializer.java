package ru.plotnikov.springcourse.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Использование Java-конфигурации, взамен web.xml в котором настраивали DispatcherServlet, и applicationContext.xml,
 * в котором перечисляли бины
 *
 * @author mplotnikov
 * @since 15.06.2021
 */
public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        //AppConfig определяет bean-компоненты, которые будут в root-context.xml
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        //определяет bean-компоненты, перечисленные в applicationСontext, и на соторые была ссылка в web.xml
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/"};
    }

    /**
     * Для необходимости фильтровать POST и преобразовывать их запросы в PATCH и DELETE - создадим и зарегистрируем
     * фильтр для чтения скрытого поля типа запроса
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        super.onStartup(servletContext);
        registerHiddenFieldFilter(servletContext);
    }

    private static void registerHiddenFieldFilter(ServletContext servletContext)
    {
        servletContext.addFilter("hiddenHttpMethodFilter",new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null
                , true, "/*");
    }
}
