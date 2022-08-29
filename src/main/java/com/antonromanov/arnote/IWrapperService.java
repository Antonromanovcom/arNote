package com.antonromanov.arnote;

import org.springframework.stereotype.Service;

@Service
public interface IWrapperService {
    Object getData(Object body);
}
