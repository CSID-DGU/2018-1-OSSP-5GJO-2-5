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

department = re.compile(r'\(주관부서.*?\)')
preDatePat = re.compile(r'\d{4}\. \d{2}\. \d{2}')
postDatePat = re.compile(r'\d{4}\.\d{2}\. \d{2}')


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

annualScheduleUrl = 'http://www.dongguk.edu/mbs/kr/jsp/academic_calender/academic_calender.jsp?academicIdx=2741&id=kr_050101000000'

driver = webdriver.Chrome()
wait = WebDriverWait(driver, 10)

driver.get(annualScheduleUrl)
scheduleListByMonth = driver.find_elements_by_class_name('schedule')
for i in range(0,len(scheduleListByMonth),1):
    scheParent = scheduleListByMonth[i].find_elements_by_class_name('schedule_list')[0].text
    #주관부서 잘라냄
    scheChild = re.sub(department,'',scheParent).replace('\n\n','\n')
    scheFinalList = scheChild.split('\n')

    for j in range(0,len(scheFinalList)-1,1):
        content = scheFinalList[j][27:]
        preDate = preDatePat.search(scheFinalList[j]).group()
        postDate = postDatePat.search(scheFinalList[j]).group()


        sql = """
        INSERT INTO dg_schedule (preDate, postDate, content) VALUES (
        %s,%s,%s)
        """



        cursor = conn.cursor()
        cursor.execute(sql,(preDate,
        postDate,
        content))
        conn.commit()
        cursor.close()


conn.close()
