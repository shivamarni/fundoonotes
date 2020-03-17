package com.bridgelabz.fundoonotes.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;
@Configuration
@EnableCaching
public class RadishCache extends CachingConfigurerSupport {
	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		
		CacheConfiguration twentySecondCache = new CacheConfiguration();
		twentySecondCache.setName("twenty-second-cache");
		twentySecondCache.setMemoryStoreEvictionPolicy("LRU");
		twentySecondCache.setMaxEntriesLocalHeap(1000);
		twentySecondCache.setTimeToLiveSeconds(20);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		
		config.addCache(twentySecondCache);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		
		return new EhCacheCacheManager(ehCacheManager());
	}
}
