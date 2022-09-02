package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.DeviceDto;
import com.aggregator.aggregator_website.dto.MonitoringPriceDto;
import com.aggregator.aggregator_website.entities.Device;
import com.aggregator.aggregator_website.entities.MonitoringPrice;
import com.aggregator.aggregator_website.repository.DeviceRepository;
import com.aggregator.aggregator_website.repository.MonitoringPriceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceService {
    private final ParsingPrice parsingPrice;
    private final ModelMapper modelMapper;
    private final DeviceRepository deviceRepository;
    private final MonitoringPriceRepository monitoringPriceRepository;

//    public List<DeviceDto> getDeviceProcessorForOffice(){
//        DeviceDto defaultDto = addDefaultDevice();
//        return findAllDevicesBySectionAndDestination("Процессор","Офис",defaultDto);
//    }
//
//    public List<DeviceDto> getDeviceProcessorForGames(){
//        DeviceDto dtoDefault = addDefaultDevice();
//        return findAllDevicesBySectionAndDestination("Процессор","Игры",dtoDefault);
//    }
//
//    public List<DeviceDto> getDeviceProcessorForHome(){
//        DeviceDto deviceDto = addDefaultDevice();
//        return findAllDevicesBySectionAndDestination("Процессор","Дом",deviceDto);
//    }

    public Device getByIdDevice(Long id){
        Device device = deviceRepository.findById(id).orElse(null);
        return device;
    }

    @Transactional
    public void addDevice(DeviceDto deviceDto, String citilinkURL,
                          String regardURL, String compmasterURL,
                          String qukeURL, String knsURL) throws IOException, NullPointerException {

        Device device = modelMapper.map(deviceDto,Device.class);


        if (!citilinkURL.isEmpty()){
            MonitoringPriceDto priceCitilinkDto = new MonitoringPriceDto();
            String logoSiteCitilink = "http://favicon.yandex.net/favicon/www.citilink.ru";
            priceCitilinkDto.setLogoSite(logoSiteCitilink);
            priceCitilinkDto.setSiteName("www.citilink.ru");
            String priceCitilink = parsingPrice.getParsePriceCitilink(citilinkURL);
            priceCitilinkDto.setLink(citilinkURL);
            if (!priceCitilink.isEmpty() && priceCitilink != null){
                priceCitilinkDto.setPrice(priceCitilink);
            }
            else {
                priceCitilinkDto.setPrice("нет в наличии");
            }
            MonitoringPrice monitoringCitilink = modelMapper.map(priceCitilinkDto,MonitoringPrice.class);
            device.getMonitoringPrices().add(monitoringCitilink);
            monitoringCitilink.setDevice(device);
        }


        if (!regardURL.isEmpty()){
            MonitoringPriceDto priceRegardDto =  new MonitoringPriceDto();
            String priceRegard = parsingPrice.getParsePriceRegard(regardURL);
            if (!priceRegard.isEmpty() && priceRegard != null){
                String res = priceRegard.replaceAll("\\D+","");
                priceRegardDto.setPrice(res);
            }
            else {
                priceRegardDto.setPrice("нет в наличии");
            }
            String logoSiteRegard = "http://favicon.yandex.net/favicon/www.regard.ru";
            priceRegardDto.setLogoSite(logoSiteRegard);
            priceRegardDto.setSiteName("www.regard.ru");
            priceRegardDto.setLink(regardURL);
            MonitoringPrice monitoringRegard = modelMapper.map(priceRegardDto,MonitoringPrice.class);
            device.getMonitoringPrices().add(monitoringRegard);
            monitoringRegard.setDevice(device);
        }

        if (!compmasterURL.isEmpty()){
            MonitoringPriceDto compmasterDto = new MonitoringPriceDto();
            String priceCompMaster = parsingPrice.getСomputerMarket(compmasterURL);
            if(!priceCompMaster.isEmpty() && priceCompMaster != null){
                compmasterDto.setPrice(priceCompMaster);
            }
            else {
                compmasterDto.setPrice("нет в наличии");
            }
            String logoSiteCompMaster = "http://favicon.yandex.net/favicon/www.computermarket.ru";
            compmasterDto.setLogoSite(logoSiteCompMaster);
            compmasterDto.setSiteName("www.computermarket.ru");
            compmasterDto.setLink(compmasterURL);
            MonitoringPrice monitoringCompMaster = modelMapper.map(compmasterDto,MonitoringPrice.class);
            device.getMonitoringPrices().add(monitoringCompMaster);
            monitoringCompMaster.setDevice(device);
        }

        if (!qukeURL.isEmpty()){
            MonitoringPriceDto qukeDto = new MonitoringPriceDto();
            String logoSiteQuke = "http://favicon.yandex.net/favicon/quke.ru";
            qukeDto.setLogoSite(logoSiteQuke);
            qukeDto.setSiteName("quke.ru");
            String priceQuke = parsingPrice.getQuke(qukeURL);
            if(!priceQuke.isEmpty() && priceQuke != null){
                qukeDto.setPrice(priceQuke);
            }
            else {
                qukeDto.setPrice("нет в наличии");
            }
            qukeDto.setLink(qukeURL);
            MonitoringPrice monitoringQuke = modelMapper.map(qukeDto,MonitoringPrice.class);
            device.getMonitoringPrices().add(monitoringQuke);
            monitoringQuke.setDevice(device);
        }

        if(!knsURL.isEmpty()){
            MonitoringPriceDto knsDto = new MonitoringPriceDto();
            String priceKNS = parsingPrice.getKNS(knsURL);
            if (!priceKNS.isEmpty() && priceKNS != null){
                knsDto.setPrice(priceKNS);
            }
            else{
                knsDto.setPrice("нет в наличии");
            }
            String logoSiteKNS = "http://favicon.yandex.net/favicon/www.kns.ru";
            knsDto.setLogoSite(logoSiteKNS);
            knsDto.setSiteName("www.kns.ru");
            knsDto.setLink(knsURL);
            MonitoringPrice monitoringKNS = modelMapper.map(knsDto,MonitoringPrice.class);
            device.getMonitoringPrices().add(monitoringKNS);
            monitoringKNS.setDevice(device);
        }

        deviceRepository.save(device);
    }

    @Transactional
    public void updateDevicePrice(Long id) throws IOException, NullPointerException {
        Device device = deviceRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<MonitoringPrice> citilinkPriceMonitoring = monitoringPriceRepository.findBySiteNameAndDevice("www.citilink.ru",device);
        if (citilinkPriceMonitoring.isPresent()){
            String citilink = parsingPrice.getParsePriceCitilink(citilinkPriceMonitoring.get().getLink());
            if (!citilink.isEmpty() && citilink !=null){
                citilinkPriceMonitoring.get().setPrice(citilink);
            }
            else {
                citilinkPriceMonitoring.get().setPrice("нет в наличии");
            }
            monitoringPriceRepository.save(citilinkPriceMonitoring.get());
        }


        Optional<MonitoringPrice> regardPriceMonitoring = monitoringPriceRepository.findBySiteNameAndDevice("www.regard.ru",device);
        if (regardPriceMonitoring.isPresent()){
            String regard = parsingPrice.getParsePriceRegard(regardPriceMonitoring.get().getLink());
            if (!regard.isEmpty() && regard !=null){
                String result = regard.replaceAll("\\D+","");
                regardPriceMonitoring.get().setPrice(result);
            }
            else {
                regardPriceMonitoring.get().setPrice("нет в наличии");
            }
            monitoringPriceRepository.save(regardPriceMonitoring.get());
        }

        Optional<MonitoringPrice> compMasterPriceMonitoring = monitoringPriceRepository.findBySiteNameAndDevice("www.computermarket.ru",device);
        if (compMasterPriceMonitoring.isPresent()){
            String compMaster = parsingPrice.getСomputerMarket(compMasterPriceMonitoring.get().getLink());
            if(!compMaster.isEmpty() && compMaster != null){
                compMasterPriceMonitoring.get().setPrice(compMaster);
            }
            else {
                compMasterPriceMonitoring.get().setPrice("нет в наличии");
            }
            monitoringPriceRepository.save(compMasterPriceMonitoring.get());
        }

        Optional<MonitoringPrice> qukePriceMonitoring = monitoringPriceRepository.findBySiteNameAndDevice("quke.ru",device);
        if(qukePriceMonitoring.isPresent()){
            String quke = parsingPrice.getQuke(qukePriceMonitoring.get().getLink());
            if(!quke.isEmpty()){
                qukePriceMonitoring.get().setPrice(quke);
            }
            else {
                qukePriceMonitoring.get().setPrice("нет в наличии");
            }
            monitoringPriceRepository.save(qukePriceMonitoring.get());
        }

        Optional<MonitoringPrice> knsPriceMonitoring = monitoringPriceRepository.findBySiteNameAndDevice("www.kns.ru",device);
        if(knsPriceMonitoring.isPresent()){
            String kns = parsingPrice.getKNS(knsPriceMonitoring.get().getLink());
            if (!kns.isEmpty()){
                knsPriceMonitoring.get().setPrice(kns);
            }
            else {
                knsPriceMonitoring.get().setPrice("нет в наличии");
            }
            monitoringPriceRepository.save(knsPriceMonitoring.get());
        }
    }

    @Transactional
    public boolean deleteDevice(Long id){
        if(deviceRepository.findById(id).isPresent()){
            deviceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteOldImage(String image){
        final String FILE_PATH = "target\\classes\\static\\";
        try {
            Files.delete(Paths.get(FILE_PATH + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DeviceDto> findAllDevicesBySectionAndDestination(String section,String destination,DeviceDto dto){
       List<Device> devices = deviceRepository.getDevicesBySectionAndDestination(section,destination);
       if (devices.size() > 0){
           return MapperConfig.convertList(devices,this::mapToDto);
       }
       else{
           List<DeviceDto> deviceDtoList = new ArrayList<>();
           deviceDtoList.add(dto);
           return deviceDtoList;
       }
    }

    public List<DeviceDto> getAllDevicesBySection(String section, DeviceDto deviceDto){
        List<Device> devices2 = deviceRepository.findAllBySection(section);
        if(devices2.size() > 0){
            return MapperConfig.convertList(devices2,this::mapToDto);
        }
        else{
            List<DeviceDto> deviceDtoList2 = new ArrayList<>();
            deviceDtoList2.add(deviceDto);
            return deviceDtoList2;
        }
    }

    public DeviceDto addDefaultDevice(){
        DeviceDto dto = new DeviceDto();
        dto.setId(Long.valueOf(1));
        dto.setName("Название устройства не указано");
        dto.setImage("img/default-comp.jpg");
        dto.setDescription("Отсутствует описание устройства...");
        dto.setSection("Не выбрано");
        dto.setDestination("Не выбрано");
        dto.setAveragePrice(0);
        dto.setMonitoringPrices(new ArrayList<>());
        MonitoringPrice monitoring = new MonitoringPrice();
        monitoring.setId(Long.valueOf(1));
        monitoring.setLogoSite("img/iconSite.jpg");
        monitoring.setSiteName("не указано");
        monitoring.setPrice("0");
        monitoring.setLink("#");
        dto.getMonitoringPrices().add(monitoring);
        return dto;
    }

    private DeviceDto mapToDto(Device device){
        DeviceDto deviceDto = modelMapper.map(device,DeviceDto.class);
        return deviceDto;
    }
}
