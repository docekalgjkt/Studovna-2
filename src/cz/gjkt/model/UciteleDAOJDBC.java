package cz.gjkt.model;

import java.util.List;

public class UciteleDAOJDBC implements IModelDao<Ucitel> {

    private static final String TABLE = "Ucitel";
    @Override
    public int insert(Ucitel object) {
        return 0;
    }

    @Override
    public int update(Ucitel object) {
        return 0;
    }

    @Override
    public int delete(Ucitel object) {
        return 0;
    }

    @Override
    public int[] insert(List<Ucitel> objects) {
        return new int[0];
    }

    @Override
    public int update(List<Ucitel> objects) {
        return 0;
    }

    @Override
    public int delete(List<Ucitel> objects) {
        return 0;
    }

    @Override
    public Ucitel getObject(int id) {
        return null;
    }

    @Override
    public List<Ucitel> getObjects(int[] ids) {
        return null;
    }

    @Override
    public List<Ucitel> getAll() {
        return null;
    }
}
