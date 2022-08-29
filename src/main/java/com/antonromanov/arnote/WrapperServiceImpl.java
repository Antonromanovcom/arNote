package com.antonromanov.arnote;

import org.springframework.stereotype.Service;

@Service
public class WrapperServiceImpl implements IWrapperService {
    @Override
    public Object getData(Object body) {
        return "Additional Information";
    }
}
