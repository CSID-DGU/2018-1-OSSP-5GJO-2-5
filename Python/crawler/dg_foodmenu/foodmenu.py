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

def download_page(url):
    maxRetry = 5
    page = ''
    while(maxRetry >= 0):
        try:
            page = urlopen(url, timeout = 10).read()
            break
        except Exception:
            time.sleep(3)
            maxRetry = maxRetry - 1
    return page

hh = html2text.HTML2Text()
hh.ignore_links = True
hh.ignore_images = True
hh.ignore_lines = False
hh.body_width = 10000

# conn = pymysql.connect(
#     db='db_dongguk_chatbot',
#     user='alex',
#     passwd='dlxkcl06',
#     host='dgchat.cg8uuo8rnebp.ap-northeast-2.rds.amazonaws.com',
#     port=3306)
# conn.set_charset('utf8')

TodayfoodmenuUrl = 'https://dgucoop.dongguk.edu/store/store.php?w=4&l=1'

driver = webdriver.Chrome()
wait = WebDriverWait(driver, 10)

driver.get(TodayfoodmenuUrl)

page = driver.page_source
soup = bs(page, 'html.parser')
detail_board = soup.find('div',{'class':'detail_board'})
menu_st_list = detail_board.findAll('td',{'class':'menu_st'})

for i in range(0, len(menu_st_list), 1):
    print(menu_st_list[i])

# sql = """
# INSERT INTO dg_foodmenu (cafeteria, foodType,
# date, foodTime, foodMenu, foodPrice) VALUES (
# %s,%s,%s,%s,%s,%s)
# """
#
#
# try:
#     cursor = conn.cursor()
#     cursor.execute(sql,(cafeteria,
#     foodType,
#     foodDate,
#     foodTime,
#     foodMenu,
#     foodPrice))
#     conn.commit()
#     cursor.close()
# except Except:
#     print('===============')
#     print('Exception occured!')
#     print('===============')
#     continue
#
# conn.close()
