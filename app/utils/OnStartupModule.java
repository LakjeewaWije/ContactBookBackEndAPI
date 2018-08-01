package utils;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import dao.ContactDao;
import dao.Impl.ContactDaoImpl;
import dao.Impl.UserDoaImpl;
import dao.UserDao;
import services.ContactService;
import services.Impl.ContactServiceImpl;
import services.Impl.UserServiceImpl;
import services.UserService;

public class OnStartupModule extends AbstractModule {

    @Override
    protected void configure() {
        //Bind dao
        bind(UserDao.class).annotatedWith(Names.named("userDao")).to(UserDoaImpl.class);
        bind(ContactDao.class).annotatedWith(Names.named("contactDao")).to(ContactDaoImpl.class);
        //bind Service
        bind(UserService.class).to(UserServiceImpl.class);
        bind(ContactService.class).to(ContactServiceImpl.class);

    }
}
