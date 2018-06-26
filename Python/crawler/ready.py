from selenium import webdriver
from bs4 import BeautifulSoup as bs

eclass_id = '2011112325'
eclass_pwd = 'dinermint1!'

# driver = webdriver.PhantomJS('/Users/alex/py_workspace/phantomjs-2.1.1-macosx/bin/phantomjs')
driver = webdriver.Chrome()
driver.implicitly_wait(1.5)
# driver.get('https://www.dongguk.edu/user/login.jsp')
driver.get('https://eclass.dongguk.edu')
driver.switch_to_frame('main')
driver.implicitly_wait(1.5)

elem = driver.find_element_by_id('id')
elem.send_keys(eclass_id)
elem = driver.find_element_by_xpath('//*[@id="pw"]')
elem.send_keys(eclass_pwd)

# driver.implicitly_wait(1.5)
# elem = driver.find_element_by_xpath('//*[@id="textInputDiv1"]/input[2]')
# elem.send_keys(eclass_pwd)

elem.submit()
