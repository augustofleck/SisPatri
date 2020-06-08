package sispatri.controller;

import java.util.List;

public interface IBaseController<T> {

    public String save(T pT);
    public String saveAll(List<T> pT);    
    public String delete(T pT);
    public String deleteAll(List<T> pT);
}
