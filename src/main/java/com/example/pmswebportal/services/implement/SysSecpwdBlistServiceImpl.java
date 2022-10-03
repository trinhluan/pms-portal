package com.example.pmswebportal.services.implement;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pmswebportal.model.SysSecpwdBlist;
import com.example.pmswebportal.repositories.SysSecpwdBlistRepository;
import com.example.pmswebportal.services.SysSecpwdBlistService;




@Service
public class SysSecpwdBlistServiceImpl implements SysSecpwdBlistService {

    @Autowired
    private SysSecpwdBlistRepository sysSecpwdBlistRepository;

    @Override
    public List<SysSecpwdBlist> getAllBlackListPass() {
        return sysSecpwdBlistRepository.findAll();
        
    }
}
