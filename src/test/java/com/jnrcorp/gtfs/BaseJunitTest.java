package com.jnrcorp.gtfs;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-resources.xml",
		"classpath:applicationContext-hibernate.xml",
		"classpath:applicationContext-services.xml",
})
@Transactional
@Ignore
public class BaseJunitTest {

}
