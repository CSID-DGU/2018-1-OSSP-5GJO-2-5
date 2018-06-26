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



nowYear = datetime.datetime.now().year

postNumPat = re.compile(r'\d{8}')
datePat = re.compile(r'\d{4}\-\d{2}\-\d{2}')
boardTagPat = re.compile(r'\[.*?\]')

def getRidOfStrongTag(s):
    return s.replace('<strong>','').replace('</strong>','')

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

conn = pymysql.connect(
    db='db_dongguk_chatbot',
    user='alex',
    passwd='dlxkcl06',
    host='dgchat.cg8uuo8rnebp.ap-northeast-2.rds.amazonaws.com',
    port=3306)
conn.set_charset('utf8')

baseUrl = 'https://www.dongguk.edu/mbs/kr/jsp/board/'
partUrl = 'list.jsp?boardId=3638&search=&column=&mcategoryId=0&boardType=01&listType=01&command=list&id=kr_010807000000'
postUrl = ''

driver = webdriver.Chrome()
wait = WebDriverWait(driver,10)



for pageNum in range(1,10,1):
    print('===================')
    print('page number : ' + str(pageNum))
    print('===================')
    time.sleep(2)
    driver.get(baseUrl + partUrl + '&spage=' + str(pageNum))
    page_source = driver.page_source
    soup = bs(page_source, 'html.parser')
    table_list = soup.select('td > a')
    for i in range(0,len(table_list),1):
        time.sleep(0.5)
        #postUrl
        postUrl = baseUrl + table_list[i].get('href')
        # //*[@id="board_list"]/tbody/tr[22]/td[1]

        page = download_page(postUrl)
        soup2 = bs(page, 'html.parser')



        boardInfo = str(soup2.select('#board_info'))

        #postNum
        postNum = postNumPat.search(boardInfo).group()

        strong_list = soup2.findAll('strong')

        #noticeType
        noticeType = getRidOfStrongTag(str(strong_list[0])).strip()

        #writer
        writer = getRidOfStrongTag(str(strong_list[2])).strip()

        #date
        date = getRidOfStrongTag(str(strong_list[3])).strip()

        #hits
        hits = getRidOfStrongTag(str(strong_list[4])).strip()

        print('noticeType : ' + noticeType + ' writer : ' + writer + ' date : ' + date + ' hits : ' + hits)

        boardView = soup2.select('#board_view')

        #title
        title = hh.handle(str(soup2.select('#board_view > thead > tr > th')))[1:-3].strip()
        print('title : ' + title)
        try:
            #postType
            postType = boardTagPat.search(title).group().replace('[','').replace(']','').strip()
        except Exception:
            postType = ''
        print('postType : ' + postType)
        boardContentParent = hh.handle(str(soup2.select('#divView')))[1:-3].replace('\n',' ').strip()

        #boardContent
        boardContent = boardContentParent[:20] + '...'
        print('boardContent : ' + boardContent)

        sql = """
        INSERT INTO dg_notice (id, postUrl, noticeType,
        writer, regDate, hits, title, type, content) VALUES (
        %s,%s,%s,%s,%s,%s,%s,%s,%s)
        """

        try:
            cursor = conn.cursor()
            cursor.execute(sql,(postNum,
            postUrl,
            noticeType,
            writer,
            date,
            hits,
            title,
            postType,
            boardContent))
            conn.commit()
            cursor.close()
        except pymysql.IntegrityError:
            print('===================')
            print('IntegrityError occured')
            print('===================')
            continue

conn.close()

    # waiter = wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR, '#board_list')))
    # elem = driver.find_element_by_id('#board_list')
    # for i in range(0,len(elem),1):
    #     print(elem[i])
