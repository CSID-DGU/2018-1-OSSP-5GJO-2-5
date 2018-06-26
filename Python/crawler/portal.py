from selenium import webdriver
from bs4 import BeautifulSoup as bs
from urllib.request import urlopen
import requests
import re

lec_num = re.compile(r'\d+')
eclass_id = '2011112325'
eclass_pwd = 'dinermint1@#'

# driver = webdriver.PhantomJS('/Users/alex/py_workspace/phantomjs-2.1.1-macosx/bin/phantomjs')
driver = webdriver.Chrome()
driver.implicitly_wait(1.5)
# driver.get('https://www.dongguk.edu/user/login.jsp')
driver.get('https://portal.dongguk.edu/member/login/login.do?sso=ok')
# driver.switch_to_frame('main')
driver.implicitly_wait(1.5)

#학생 교직원 로그인
elem1 = driver.find_element_by_id('userId')
elem1.send_keys(eclass_id)
elem1 = driver.find_element_by_id('userPwd')
elem1.send_keys(eclass_pwd)

elem2 = driver.find_element_by_xpath('//*[@id="bgS"]/div/div[1]/div[3]/a')
elem2.click()

print(driver.page_source)
# driver.implicitly_wait(1.5)
# elem = driver.find_element_by_xpath('//*[@id="textInputDiv1"]/input[2]')
# elem.send_keys(eclass_pwd)
