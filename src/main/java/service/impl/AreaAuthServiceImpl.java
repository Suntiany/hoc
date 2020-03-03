package service.impl;

import dao.AreaAuthDao;
import entity.AreaAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AreaAuthService;

@Service
public class AreaAuthServiceImpl implements AreaAuthService {
    @Autowired
    private AreaAuthDao areaAuthDao;
    @Override
    public AreaAuth Login(String username, String password) {
        return areaAuthDao.login(username,password);
    }
}
