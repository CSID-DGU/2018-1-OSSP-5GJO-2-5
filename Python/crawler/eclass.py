from urllib.request import urlopen
from bs4 import BeautifulSoup as bs
import os
import requests
import pathlib
import zipfile
import html2text
from selenium import webdriver

driver = webdriver.Chrome()
# driver = webdriver.phantomjs('/Users/alex/py_workspace/phantomjs-2.1.1-macosx/bin/phantomjs')
driver.get('https://eclass.dongguk.edu/index.jsp')
html = driver.page_source
soup3 = bs(html)
div = soup3.findAll('div')
print(str(div))

h2t = html2text.HTML2Text()
h2t.ignore_links = True
h2t.ignore_images = True
h2t.ignore_lines = True
h2t.body_width = 10000

eclass_url = urlopen('https://eclass.dongguk.edu/index.jsp',timeout=10).read()
soup = bs(eclass_url, 'html.parser', from_encoding='utf-8')
eclass_id = '2011112325'
eclass_pwd = 'dinermint1!'
eclass_html = soup.find('ul',{'class':'user_box'})

google_url = urlopen('http://google.com')
soup2 = bs(google_url, 'html.parser')
str1 = soup2.find('html')
