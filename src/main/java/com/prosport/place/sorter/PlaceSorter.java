package com.prosport.place.sorter;

import com.prosport.place.entity.Infrastructure;
import com.prosport.place.entity.Place;
import com.prosport.place.entity.Sport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Vlad Milyutin.
 */
@Component("placeSorter")
public class PlaceSorter{

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public Collection<Place> sortByInfrastructure(Collection<Place> places, String requestParam)
            throws Exception{
        LOG.info("'sortByInfrastructure' invoked with params: '{}', '{}'", places, requestParam);

        String[] requestParams = requestParam.split(",");

        List<Place> sortedList = new ArrayList<>();

        for (Place place: places) {
            Infrastructure infrastructure = place.getInfrastructure();
            Class aClazz = infrastructure.getClass();
            LOG.info("Checking: '{}'", infrastructure);
            boolean shouldAdd = true;
            for (String param : requestParams){
                LOG.info("Param: '{}'", param);
                Method method = aClazz.getMethod("is"+param);
                Boolean methodResponse = (boolean) method.invoke(infrastructure);
                if(methodResponse){
                    LOG.info("TRUE: '{}'", param);
                    shouldAdd = true;
                }
                else{
                    LOG.info("FALSE: '{}'", param);
                    shouldAdd = false;
                    break;
                }
            }
            if(shouldAdd){
                LOG.info("Adding to sorted list: '{}'", place);
                sortedList.add(place);
            }
        }

        LOG.info("'sortByInfrastructure({},{}) returned: {}, {}, {}", sortedList, requestParam, places);
        return sortedList;
    }

    public Collection<Place> sortBySportType(Collection<Place> places, String requestParam)
            throws Exception{
        LOG.info("'sortBySportType' invoked with params: '{}', '{}'", places, requestParam);

        String[] requestParams = requestParam.split(",");

        List<Place> sortedList = new ArrayList<>();

        for (Place place: places) {
            Sport sport = place.getSportTypes();
            Class aClazz = sport.getClass();
            boolean shouldAdd = true;
            LOG.info("Checking: '{}'", sport);
            for (String param : requestParams){
                LOG.info("Param: '{}'", param);
                Method method = aClazz.getMethod("is"+param);
                Boolean methodResponse = (boolean) method.invoke(sport);
                if(methodResponse){
                    LOG.info("TRUE: '{}'", param);
                    shouldAdd = true;
                }
                else{
                    LOG.info("FALSE: '{}'", param);
                    shouldAdd = false;
                    break;
                }
            }
            if(shouldAdd){
                LOG.info("Adding to sorted list: '{}'", place);
                sortedList.add(place);
            }
        }

        LOG.info("'sortBySport({},{}) returned: {}, {}, {}", sortedList, requestParam, places);
        return sortedList;
    }
}
