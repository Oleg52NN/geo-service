import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static ru.netology.entity.Country.*;
import static ru.netology.geo.GeoServiceImpl.MOSCOW_IP;

public class GeoServiceImplTest {

    @Test
    void test_Geo(){
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(geoService.byIp("172."))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(geoService.byIp("96."))
                .thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(USA))
                .thenReturn("Welcome");
        MessageSenderImpl received = new MessageSenderImpl(geoService,localizationService);
        Map<String, String> testHeaders = new HashMap<String, String>();
        testHeaders.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.");
        Assertions.assertEquals("Добро пожаловать", received.send(testHeaders));        System.out.println();
        testHeaders.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.");
        Assertions.assertEquals("Welcome", received.send(testHeaders));
        System.out.println();
        testHeaders.put(MessageSenderImpl.IP_ADDRESS_HEADER, "");
        Assertions.assertEquals("Welcome", received.send(testHeaders));


    }
}
