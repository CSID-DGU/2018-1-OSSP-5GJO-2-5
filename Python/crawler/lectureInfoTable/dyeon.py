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
conn = pymysql.connect(
    db='db_test',
    user='root',
    passwd='root',
    host='localhost',
    port=8889)
conn.set_charset('utf8')
# conn = pymysql.connect(
#     db='db_test',
#     user='root',
#     passwd='root',
#     host='localhost',
#     port=8889)
# conn.set_charset('utf8')
# cursor = conn.cursor()
#regular expressions
lec_num = re.compile(r'\d+')
lectureProf = re.compile(r'\(.*?\)')
lectureSearchResultNum = re.compile(r'lecture\_search\_result\_\d+')


#define
lectureName = ""
lectureNum = ""
lectureProf = ""
lectureTime = ""
lectureRoom1 = ""
lectureRoom2 = ""
lectureMajor = ""
goods = ""
bads = ""
comments = ""

dyeon_id = 'dinermint0625'
dyeon_pwd = 'mfkqezfb'

# driver = webdriver.PhantomJS('/Users/alex/py_workspace/phantomjs-2.1.1-macosx/bin/phantomjs')
driver = webdriver.Chrome()

#waiter
wait = WebDriverWait(driver, 10)

driver.implicitly_wait(1.5)
# driver.get('https://www.dongguk.edu/user/login.jsp')
driver.get('https://dyeon.net/user/login')
# driver.switch_to_frame('main')
driver.implicitly_wait(1.5)

elem1 = driver.find_element_by_css_selector('#user_login')
elem1.send_keys(dyeon_id)
elem1 = driver.find_element_by_css_selector('#user_password')
elem1.send_keys(dyeon_pwd)

# driver.implicitly_wait(1.5)
# elem = driver.find_element_by_xpath('//*[@id="textInputDiv1"]/input[2]')
# elem.send_keys(eclass_pwd)
elem1.submit()

driver.get('https://dyeon.net/timetable')
# for k in range(2,95,1): <- original
for k in range(37,95,1):
    driver.get('https://dyeon.net/timetable')

    # waiter5 = WebDriverWait(driver, 10).until(EC.visibility_of_any_elements_located((By.CSS_SELECTOR, 'span.span7 > table > tbody > tr')))
    print('==========================================')
    print('                 index : ' + str(k))
    print('==========================================')

    elem2 = driver.find_element_by_css_selector('#lecture_major_id')
    elem2.click()
    elem2 = driver.find_element_by_css_selector('#lecture_major_id > option:nth-child(' + str(k) + ')')
    elem2.click()

    lectures = driver.find_element_by_id('lecture_search_result')
    # for i in range(59752, 59742, -1):
    #     try:
    #         print(driver.find_element_by_id('lecture_search_result_' + str(i)).text)
    #         driver.implicitly_wait(0.5)
    #     except Exception:
    #         continue
    waiter = wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR,'#lecture_search_result > table > tbody > tr')))
    list_lec = lectures.find_elements_by_tag_name('tr')
    # list_lec = wait.until(EC.visibility_of_any_elements_located((By.CSS_SELECTOR,'#lecture_search_result > table > tbody > ')))
    # print('1 : ' + waiter1.text + ' 2 : ' + waiter2.text + ' 3 : ' + waiter3.text + ' 4 : ' + waiter4.text)
    # for j in range(0,len(list_lec),1): <- 원시코드
    for j in range(0,10,1):
        try:
            list_line = str(list_lec[j].text).split('\n')
            if len(list_line) < 4 :
                continue
        except Exception:
            continue
        for i in range(0,len(list_line),1):
            # print(str(i) + ':' + list_line[i])
            driver.implicitly_wait(2)
            print('index : ' + str(i))
            if i == 0 :
                list_n = list_line[i].split(' ')
                goods = list_n[0]
                bads = list_n[1]
                comments = list_n[2]
                print('goods : ' + str(goods) + ' / bads : ' + str(bads) + ' / comments : ' + str(comments))
            if i == 1 :
                list_n = list_line[i].split(' ')
                lectureName = list_n[0]
                lectureProf = list_n[1].replace('(','').replace(')','')
                print('lectureName : ' + lectureName + ' / lectureProf : ' + lectureProf)

            if i == 2 :
                list_n = list_line[i].split(' ')
                if len(list_n) == 3:
                    lectureNum = list_n[0]
                    lectureTime = list_n[1]
                    lectureCred = list_n[2].replace('학점','')
                    print('lectureNum : ' + lectureNum + ' / lectureTime : ' + lectureTime)
                else:

                    lectureNum = list_n[0]
                    lectureTime = ''
                    lectureCred = list_n[1].replace('학점','')
                    print('lectureNum : ' + lectureNum + ' / lectureTime : ' + lectureTime)
            if i == 3 :
                if len(list_line[i]) > 15:
                    lectureRoom1 = list_line[i]
                    print('lectureRoom1 : ' + lectureRoom1)
                else:
                    lectureMajor = list_line[i]
                    lectureRoom1 = ''
                    lectureRoom2 = ''
                    continue
            if i == 4 :
                if str(list_line[i])[-1:] == ')':
                    lectureRoom2 = list_line[i]
                    print('lectureRoom2 : ' + lectureRoom2)
                else:
                    lectureRoom2 = ''
                    lectureMajor = list_line[i]
                    print('lectureRoom2 : ' + lectureRoom2)
                    print('lectureMajor : ' + lectureMajor)
            if i == 5 :
                # if len(str(list_line[i])) > 1:
                lectureMajor = list_line[i]
                print('lectureMajor : ' + lectureMajor)

            # sql = 'insert into tb_test (lectureName, lectureNum, lectureProf, lectureTime, lectureRoom1, lectureRoom2, lectureMajor, lectureGoods, lectureBads, lectureComments) values (' + lectureName + ', ' + lectureNum + ', ' + lectureProf + ', ' + lectureTime + ', ' + lectureRoom1 + ', ' + lectureRoom2 + ', ' + lectureMajor + ', ' + goods + ', ' + bads + ', ' + comments + ');'
        sql ="""
        INSERT INTO tb_test (id ,lectureName, lectureProf,
        lectureTime, lectureRoom1, lectureRoom2, lectureMajor,
        lectureGoods, lectureBads, lectureComments) VALUES (
        %s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
        """

        try:
            cursor = conn.cursor()
            cursor.execute(sql,(lectureNum,
            lectureName,
            lectureProf,
            lectureTime,
            lectureRoom1,
            lectureRoom2,
            lectureMajor,
            goods,bads,comments))
            conn.commit()
            cursor.close()
        except Exception:
            continue

conn.close()


        # for i in range(0,len(list_lec),1):
        #     print(list_lec[i].text)
