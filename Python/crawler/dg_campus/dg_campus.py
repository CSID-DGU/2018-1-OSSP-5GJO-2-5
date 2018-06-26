from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup as bs
from urllib.request import urlopen
import requests
import re
import pymysql
import time
import datetime
import html2text

hh = html2text.HTML2Text()
hh.ignore_links = True
hh.ignore_images = True
hh.ignore_lines = False
hh.body_width = 10000



def get_text_excluding_children(driver, element):
    return driver.execute_script("""
    return jQuery(arguments[0]).contents().filter(function() {
        return this.nodeType == Node.TEXT_NODE;
    }).text();
    """, element)

url = 'http://www.dongguk.edu/mbs/kr/html/new_map/dgu_map.htm'

driver = webdriver.Chrome()

driver.get(url)



driver_ps = driver.page_source
soup = bs(driver_ps, 'html.parser')
tour_menu = soup.find('div',{'class':'tour_menu'})
tour_menu_list = tour_menu.findAll('img')

for i in range(0,len(tour_menu_list),1):
    building_name = tour_menu_list[i].get('alt'))
    
