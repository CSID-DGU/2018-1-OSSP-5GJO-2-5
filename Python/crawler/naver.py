from selenium import webdriver
from bs4 import BeautifulSoup as bs

naver_id = 'dbrmsckd0625'
naver_pwd = 'dinermint1!'

driver = webdriver.Chrome()
# driver = webdriver.Chrome()
driver.implicitly_wait(1.5)
driver.get('https://nid.naver.com/nidlogin.login')
# driver.switch_to_frame('main')

elem = driver.find_element_by_id('id')
elem.send_keys(naver_id)
elem = driver.find_element_by_id('pw')
elem.send_keys(naver_pwd)
elem.submit()
