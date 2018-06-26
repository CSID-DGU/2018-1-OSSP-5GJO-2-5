from selenium import webdriver
from urllib.request import urlopen
from bs4 import BeautifulSoup as bs
import requests
import time
import pathlib
import zipfile
import os



book_id = '9781491962282'
safari_url = 'https://www.safaribooksonline.com/library/view/hands-on-machine-learning/'

safari_id = 'dust625@gmail.com'
safari_pwd = 'dinermint1!'
##############
#   LOGIN
##############
driver = webdriver.Chrome()
driver.get('https://www.safaribooksonline.com/')
elem1 = driver.find_element_by_css_selector('#home > header > nav > div.sign-in > a')
elem1.click()
elem1 = driver.find_element_by_css_selector('#id_email')
elem1.send_keys(safari_id)
elem1 = driver.find_element_by_css_selector('#id_password1')
elem1.send_keys(safari_pwd)
elem1.submit()

start_index = 1
end_index = 18

restore_path = './hands-on/'

pathlib.Path(restore_path).mkdir(parents=True, exist_ok=True)
web_fail = open(restore_path + 'web_fail.txt','w')

def book_path(s):
    return safari_url + book_id + '/'

chapter_path = 'https://www.safaribooksonline.com/library/view/hands-on-machine-learning/9781491962282/'

def download_page(url):
    maxRetry = 5
    page = ""
    while (maxRetry >= 0):
        try:
            page = urlopen(url, timeout = 10).read()
            break
        except Exception:
            time.sleep(5)
            maxRetry = maxRetry - 1
    return page

for i in range(start_index,end_index,1):
    time.sleep(3)
    if i < 10 and i > 0:
        driver.get(chapter_path + 'ch0' + str(i) + '.html')
    else:
        driver.get(chapter_path + 'ch' + str(i) + '.html')
    # elem2 = driver.find_element_by_css_selector('html')
    html = driver.page_source
    source_file = restore_path + str(i) + '.html'
    with open(source_file, 'w') as page:
        page.write(html)
