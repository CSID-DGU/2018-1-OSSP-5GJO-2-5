from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

driver = webdriver.Chrome()
driver.get('https://kakaobank.com')

try:
    title = WebDriverWait(driver, 10) \
    .until(EC.presence_of_element_located((By.CSS_SELECTOR, "div.intro_main > h3")))
    print(title.text)
finally:
    driver.quit()
