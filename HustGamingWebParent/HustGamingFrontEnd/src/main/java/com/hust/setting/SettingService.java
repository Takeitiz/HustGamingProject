package com.hust.setting;

import com.hust.common.entity.setting.Setting;
import com.hust.common.entity.setting.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingRepository repo;
    public List<Setting> getGeneralSettings() {
        return repo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }
    public EmailSettingBag getEmailSettings() {
        List<Setting> settings = repo.findByCategory(SettingCategory.MAIL_SERVER);
        settings.addAll(repo.findByCategory(SettingCategory.MAIL_TEMPLATES));

        return new EmailSettingBag(settings);
    }
    public CurrencySettingBag getCurrencySettings() {
        List<Setting> settings = repo.findByCategory(SettingCategory.CURRENCY);
        return new CurrencySettingBag(settings);
    }
}
