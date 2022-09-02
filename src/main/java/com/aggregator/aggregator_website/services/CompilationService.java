package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.CompilationDto;
import com.aggregator.aggregator_website.dto.CompilationRequest;
import com.aggregator.aggregator_website.dto.ProductCreateDto;
import com.aggregator.aggregator_website.entities.Compilation;
import com.aggregator.aggregator_website.entities.Device;
import com.aggregator.aggregator_website.entities.Product;
import com.aggregator.aggregator_website.repository.CompilationRepository;
import com.aggregator.aggregator_website.repository.DeviceRepository;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompilationService {
    private final CompilationRepository compilationRepository;
    private final ModelMapper modelMapper;
    private final DeviceRepository deviceRepository;

    public List<CompilationDto> getAllCompilations(){
        List<Compilation> compilations = compilationRepository.getAllCompilationsByLastCurrentTime();
        if(compilations.size() > 0){
            return MapperConfig.convertList(compilations,this::mapToDto);
        }
        else{
            List<CompilationDto> compilationDTOs = new ArrayList<>();
            CompilationDto cd = getDefaultCompilation();
            compilationDTOs.add(cd);

            return compilationDTOs;
        }
    }

    @Transactional
    public void createCompilation(ProductCreateDto productDto, String avatar,
                                  String firstName, String secondName){
        CompilationDto compDto = new CompilationDto();
        LocalDateTime dateT = LocalDateTime.now();
        String current = dateT.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime currentDateT = LocalDateTime.parse(current,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        compDto.setCurrentTime(currentDateT);
        compDto.setAvatarUser(avatar);
        compDto.setFirstName(firstName);
        compDto.setSecondName(secondName);
        compDto.setDestiny(productDto.getDestiny());
        compDto.setProducts(new ArrayList<>());

        Compilation comp = modelMapper.map(compDto, Compilation.class);

        if(!productDto.getProcessorBrand().isEmpty() && productDto.getProcessorPrice() > 0){
            Product cpu = new Product();
            cpu.setDeviceName(productDto.getProcessorBrand());
            cpu.setPrice(productDto.getProcessorPrice());
            comp.getProducts().add(cpu);
            cpu.setCompilation(comp);
        }

        if(!productDto.getMotherboardBrand().isEmpty() && productDto.getMotherboardPrice() > 0){
            Product mat = new Product();
            mat.setDeviceName(productDto.getMotherboardBrand());
            mat.setPrice(productDto.getMotherboardPrice());
            comp.getProducts().add(mat);
            mat.setCompilation(comp);
        }

        if(!productDto.getCpuCoolerBrand().isEmpty() && productDto.getCpuCoolerPrice() > 0){
            Product coolerCPU = new Product();
            coolerCPU.setDeviceName(productDto.getCpuCoolerBrand());
            coolerCPU.setPrice(productDto.getCpuCoolerPrice());
            comp.getProducts().add(coolerCPU);
            coolerCPU.setCompilation(comp);
        }

        if(!productDto.getRamBrand().isEmpty() && productDto.getRamPrice() > 0){
            Product operative = new Product();
            operative.setDeviceName(productDto.getRamBrand());
            operative.setPrice(productDto.getRamPrice());
            comp.getProducts().add(operative);
            operative.setCompilation(comp);
        }

        if(!productDto.getSddBrand().isEmpty() && productDto.getSddPrice() > 0){
            Product ssd2 = new Product();
            ssd2.setDeviceName(productDto.getSddBrand());
            ssd2.setPrice(productDto.getSddPrice());
            comp.getProducts().add(ssd2);
            ssd2.setCompilation(comp);
        }

        if(!productDto.getHddBrand().isEmpty() && productDto.getHddPrice() > 0){
            Product hdd2 = new Product();
            hdd2.setDeviceName(productDto.getHddBrand());
            hdd2.setPrice(productDto.getHddPrice());
            comp.getProducts().add(hdd2);
            hdd2.setCompilation(comp);
        }

        if(!productDto.getPowerSupplyBrand().isEmpty() && productDto.getPowerSupplyPrice() > 0){
            Product block = new Product();
            block.setDeviceName(productDto.getPowerSupplyBrand());
            block.setPrice(productDto.getPowerSupplyPrice());
            comp.getProducts().add(block);
            block.setCompilation(comp);
        }

        if(!productDto.getVideoCardBrand().isEmpty() && productDto.getVideoCardPrice() > 0){
            Product videoC = new Product();
            videoC.setDeviceName(productDto.getVideoCardBrand());
            videoC.setPrice(productDto.getVideoCardPrice());
            comp.getProducts().add(videoC);
            videoC.setCompilation(comp);
        }

        if(!productDto.getCoolerBrand().isEmpty() && productDto.getCoolerPrice() > 0){
            Product coolerCorpus = new Product();
            coolerCorpus.setDeviceName(productDto.getCoolerBrand());
            coolerCorpus.setPrice(productDto.getCoolerPrice());
            comp.getProducts().add(coolerCorpus);
            coolerCorpus.setCompilation(comp);
        }

        if(!productDto.getCorpusBrand().isEmpty() && productDto.getCorpusPrice() > 0){
            Product corpus2 = new Product();
            corpus2.setDeviceName(productDto.getCorpusBrand());
            corpus2.setPrice(productDto.getCorpusPrice());
            comp.getProducts().add(corpus2);
            corpus2.setCompilation(comp);
        }

        if(!productDto.getMonitorBrand().isEmpty() && productDto.getMonitorPrice() > 0){
            Product monitor2 = new Product();
            monitor2.setDeviceName(productDto.getMonitorBrand());
            monitor2.setPrice(productDto.getMonitorPrice());
            comp.getProducts().add(monitor2);
            monitor2.setCompilation(comp);
        }

        if(!productDto.getMouseBrand().isEmpty() && productDto.getMousePrice() > 0){
            Product mouse2 = new Product();
            mouse2.setDeviceName(productDto.getMouseBrand());
            mouse2.setPrice(productDto.getMousePrice());
            comp.getProducts().add(mouse2);
            mouse2.setCompilation(comp);
        }

        if(!productDto.getKeyboardBrand().isEmpty() && productDto.getKeyboardPrice() > 0){
            Product keyboard2 = new Product();
            keyboard2.setDeviceName(productDto.getKeyboardBrand());
            keyboard2.setPrice(productDto.getKeyboardPrice());
            comp.getProducts().add(keyboard2);
            keyboard2.setCompilation(comp);
        }

        if(!productDto.getMfdBrand().isEmpty() && productDto.getMfdPrice() > 0){
            Product mfd2 = new Product();
            mfd2.setDeviceName(productDto.getMfdBrand());
            mfd2.setPrice(productDto.getMfdPrice());
            comp.getProducts().add(mfd2);
            mfd2.setCompilation(comp);
        }

        if(comp.getProducts().size() > 0){
            compilationRepository.save(comp);
        }

    }

    @Transactional
    public void addCompilation(CompilationRequest request, String avatar,
                               String firstName, String secondName){
        CompilationDto compilationDto1 = getCompilationDto(request.getDestiny(), avatar, firstName, secondName);
        Compilation compilation = modelMapper.map(compilationDto1, Compilation.class);

        if(!request.getProcessorName().isEmpty() && request.getProcessorName() != null){
            Product processor = new Product();
            Optional<Device> procDevice = deviceRepository.findByName(request.getProcessorName());
            if(procDevice.isPresent()){
                processor.setDeviceName(procDevice.get().getName());
                processor.setPrice(procDevice.get().getAveragePrice());
                compilation.getProducts().add(processor);
                processor.setCompilation(compilation);
            }
        }

        if(!request.getMotherboardName().isEmpty() && request.getMotherboardName() != null){
            Product motherboard = new Product();
            Optional<Device> motherBDevice = deviceRepository.findByName(request.getMotherboardName());
            if(motherBDevice.isPresent()){
                motherboard.setDeviceName(motherBDevice.get().getName());
                motherboard.setPrice(motherBDevice.get().getAveragePrice());
                compilation.getProducts().add(motherboard);
                motherboard.setCompilation(compilation);
            }
        }

        if(!request.getCpuCoolerName().isEmpty() && request.getCpuCoolerName() != null){
            Product cpuCooler = new Product();
            Optional<Device> cpuCoolerDevice = deviceRepository.findByName(request.getCpuCoolerName());
            if(cpuCoolerDevice.isPresent()){
                cpuCooler.setDeviceName(cpuCoolerDevice.get().getName());
                cpuCooler.setPrice(cpuCoolerDevice.get().getAveragePrice());
                compilation.getProducts().add(cpuCooler);
                cpuCooler.setCompilation(compilation);
            }
        }

        if(!request.getRamName().isEmpty() && request.getRamName() != null){
            Product ram = new Product();
            Optional<Device> ramDevice = deviceRepository.findByName(request.getRamName());
            if(ramDevice.isPresent()){
                ram.setDeviceName(ramDevice.get().getName());
                ram.setPrice(ramDevice.get().getAveragePrice());
                compilation.getProducts().add(ram);
                ram.setCompilation(compilation);
            }
        }

        if(!request.getSddName().isEmpty() && request.getSddName() != null){
            Product ssd = new Product();
            Optional<Device> ssdDevice = deviceRepository.findByName(request.getSddName());
            if(ssdDevice.isPresent()){
                ssd.setDeviceName(ssdDevice.get().getName());
                ssd.setPrice(ssdDevice.get().getAveragePrice());
                compilation.getProducts().add(ssd);
                ssd.setCompilation(compilation);
            }
        }

        if(!request.getHddName().isEmpty() && request.getHddName() != null){
            Product hdd = new Product();
            Optional<Device> hddDevice = deviceRepository.findByName(request.getHddName());
            if(hddDevice.isPresent()){
                hdd.setDeviceName(hddDevice.get().getName());
                hdd.setPrice(hddDevice.get().getAveragePrice());
                compilation.getProducts().add(hdd);
                hdd.setCompilation(compilation);
            }
        }

        if(!request.getVideoCardName().isEmpty() && request.getVideoCardName() != null){
            Product videoCard = new Product();
            Optional<Device> videoDevice = deviceRepository.findByName(request.getVideoCardName());
            if(videoDevice.isPresent()){
                videoCard.setDeviceName(videoDevice.get().getName());
                videoCard.setPrice(videoDevice.get().getAveragePrice());
                compilation.getProducts().add(videoCard);
                videoCard.setCompilation(compilation);
            }
        }

        if(!request.getPowerSupplyName().isEmpty() && request.getPowerSupplyName() != null){
            Product powerS = new Product();
            Optional<Device> powerSDevice = deviceRepository.findByName(request.getPowerSupplyName());
            if(powerSDevice.isPresent()){
                powerS.setDeviceName(powerSDevice.get().getName());
                powerS.setPrice(powerSDevice.get().getAveragePrice());
                compilation.getProducts().add(powerS);
                powerS.setCompilation(compilation);
            }
        }

        if(!request.getCoolerName().isEmpty() && request.getCoolerName() != null){
            Product cooler = new Product();
            Optional<Device> coolerDevice = deviceRepository.findByName(request.getCoolerName());
            if (coolerDevice.isPresent()){
                cooler.setDeviceName(coolerDevice.get().getName());
                cooler.setPrice(coolerDevice.get().getAveragePrice());
                compilation.getProducts().add(cooler);
                cooler.setCompilation(compilation);
            }
        }

        if(!request.getCorpusName().isEmpty() && request.getCorpusName() != null){
            Product corpus = new Product();
            Optional<Device> corpusDevice = deviceRepository.findByName(request.getCorpusName());
            if(corpusDevice.isPresent()){
                corpus.setDeviceName(corpusDevice.get().getName());
                corpus.setPrice(corpusDevice.get().getAveragePrice());
                compilation.getProducts().add(corpus);
                corpus.setCompilation(compilation);
            }
        }

        if(!request.getMonitorName().isEmpty() && request.getMonitorName() != null){
            Product monitor = new Product();
            Optional<Device> monitorDevice = deviceRepository.findByName(request.getMonitorName());
            if(monitorDevice.isPresent()){
                monitor.setDeviceName(monitorDevice.get().getName());
                monitor.setPrice(monitorDevice.get().getAveragePrice());
                compilation.getProducts().add(monitor);
                monitor.setCompilation(compilation);
            }
        }

        if(!request.getMouseName().isEmpty() && request.getMouseName() != null){
            Product mouse = new Product();
            Optional<Device> mouseDevice = deviceRepository.findByName(request.getMouseName());
            if(mouseDevice.isPresent()){
                mouse.setDeviceName(mouseDevice.get().getName());
                mouse.setPrice(mouseDevice.get().getAveragePrice());
                compilation.getProducts().add(mouse);
                mouse.setCompilation(compilation);
            }
        }

        if(!request.getKeyboardName().isEmpty() && request.getKeyboardName() != null){
            Product keyboard = new Product();
            Optional<Device> keyboardDevice = deviceRepository.findByName(request.getKeyboardName());
            if(keyboardDevice.isPresent()){
                keyboard.setDeviceName(keyboardDevice.get().getName());
                keyboard.setPrice(keyboardDevice.get().getAveragePrice());
                compilation.getProducts().add(keyboard);
                keyboard.setCompilation(compilation);
            }
        }

        if (!request.getMfdName().isEmpty() && request.getMfdName() != null){
            Product mfd = new Product();
            Optional<Device> mfdDevice = deviceRepository.findByName(request.getMfdName());
            if(mfdDevice.isPresent()){
                mfd.setDeviceName(mfdDevice.get().getName());
                mfd.setPrice(mfdDevice.get().getAveragePrice());
                compilation.getProducts().add(mfd);
                mfd.setCompilation(compilation);
            }
        }

        if(compilation.getProducts().size() > 0){
            compilationRepository.save(compilation);
        }
    }

    @Transactional
    public void deleteCompilation(@RequestParam("id") Long id){
        Compilation compilation = compilationRepository.findById(id).orElse(null);
        if(compilation != null){
            compilationRepository.delete(compilation);
        }
    }

    private CompilationDto getCompilationDto(String destiny, String avatar, String firstName, String secondName) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setAvatarUser(avatar);
        compilationDto.setFirstName(firstName);
        compilationDto.setSecondName(secondName);
        compilationDto.setDestiny(destiny);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formatStr = currentDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime nowDateTime = LocalDateTime.parse(formatStr,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        compilationDto.setCurrentTime(nowDateTime);
        compilationDto.setProducts(new ArrayList<>());
        return compilationDto;
    }

    private CompilationDto mapToDto(Compilation compilation){
        CompilationDto compilationDto = modelMapper.map(compilation,CompilationDto.class);
        return compilationDto;
    }

    private CompilationDto getDefaultCompilation(){
        CompilationDto compDto = new CompilationDto();
        compDto.setId(Long.valueOf(0));
        compDto.setAvatarUser("avatars/ty.jpg");
        compDto.setFirstName("Иван");
        compDto.setSecondName("Иванов");
        String localtime = "01.01.2022 13:20";
        LocalDateTime currentDateTime2 = LocalDateTime.parse(localtime,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        compDto.setCurrentTime(currentDateTime2);
        compDto.setDestiny("Домашний ПК");
        compDto.setProducts(new ArrayList<>());

        Product productCpu = new Product();
        productCpu.setId(Long.valueOf(1));
        productCpu.setDeviceName("Intel Core i5 12400F OEM");
        productCpu.setPrice(14990);
        productCpu.setCompilation(new Compilation());
        compDto.getProducts().add(productCpu);

        Product mp = new Product();
        mp.setId(Long.valueOf(2));
        mp.setDeviceName("ASUS PRIME B660M-K D4 mATX LGA1700");
        mp.setPrice(9990);
        mp.setCompilation(new Compilation());
        compDto.getProducts().add(mp);

        Product productRAM = new Product();
        productRAM.setId(Long.valueOf(3));
        productRAM.setDeviceName("Оперативная память 16Gb G.Skill Trident Z 2x8Gb KIT CL16 DDR4");
        productRAM.setPrice(8299);
        productRAM.setCompilation(new Compilation());
        compDto.getProducts().add(productRAM);

        Product bp = new Product();
        bp.setId(Long.valueOf(4));
        bp.setDeviceName("Блок питания 600W Be Quiet System Power 9 Bronze BN247");
        bp.setPrice(4899);
        bp.setCompilation(new Compilation());
        compDto.getProducts().add(bp);

        Product vc = new Product();
        vc.setId(Long.valueOf(5));
        vc.setDeviceName("Видеокарта ASUS GeForce RTX 3060 Ti DUAL OC 8GB LHR");
        vc.setPrice(47990);
        vc.setCompilation(new Compilation());
        compDto.getProducts().add(vc);

        Product cooler = new Product();
        cooler.setId(Long.valueOf(6));
        cooler.setDeviceName("Кулер Be Quiet Pure Rock Slim BK008");
        cooler.setPrice(2199);
        cooler.setCompilation(new Compilation());
        compDto.getProducts().add(cooler);

        Product corp = new Product();
        corp.setId(Long.valueOf(7));
        corp.setDeviceName("Корпус Thermaltake Versa C24 RGB Snow Edition");
        corp.setPrice(4820);
        corp.setCompilation(new Compilation());
        compDto.getProducts().add(corp);

        return compDto;
    }
}
