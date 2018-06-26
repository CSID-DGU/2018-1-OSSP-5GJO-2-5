import pymysql
import html2text

class mysqlConnectInfo:
    def setConnect():
        conn = pymysql.connect(
            db='db_dongguk_chatbot',
            user='alex',
            passwd='dlxkcl06',
            host='dgchat.cg8uuo8rnebp.ap-northeast-2.rds.amazonaws.com',
            port=3306
        )
        conn.set_charset('utf8')
        return conn

class utilDef:
        def getHtml2Text():
            hh = html2text.HTML2Text()
            hh.ignore_links = True
            hh.ignore_images = True
            hh.ignore_lines = True
            hh.body_width = 10000
            return hh

        def getRidOfStrongTag(s):
            res = s.replace('<strong>','').replace('</strong>','')
            return res
