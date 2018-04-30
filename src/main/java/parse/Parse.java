package parse;

import configuration.Configuration;

/**
 * Created by Administrator on 2018/4/19.
 */
public abstract class Parse {


    protected Configuration configuration;

    protected Parse(Configuration configuration) {
        this.configuration = configuration;
    }




    public abstract void parse();
}
