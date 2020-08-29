from flask import Flask,request,json,jsonify
import pymysql
import jieba

app = Flask(__name__)

conn  = pymysql.Connect(
    host='xxxxxxxxxx',
    port=3306,
    user='root',
    password='xxxxxxx',
    db='forum',
    charset='utf8'
)

@app.route('/getsearch',methods=['POST'])
def search_handle():
    keyword = request.form.get("data")

    if keyword==None or keyword=='':
        return jsonify({'code':0,'msg':'Params Error','data':None})
    keywords = jieba.lcut_for_search(keyword)
    keywords = [x.strip() for x in keywords if x.strip()!='']
    keystr='|'.join(keywords)

    result,ok=search_dao(keystr)
    if ok:
        return jsonify({'code':0,'msg':'Success','data':result})
    else:
        return jsonify({'code':0,'msg':'Server Error','data':None})

def search_dao(keyword):
    cursor = conn.cursor()
    sql = "select id,title from articles where title regexp '%s' or content regexp '%s'"%(keyword,keyword)
    try:
        rows=cursor.execute(sql)
        if rows>0:
            datas_list = cursor.fetchall()
            fields_list = cursor.description
            column_list = []
            for i in fields_list:
                column_list.append(i[0])
            data_result = []
            for data in datas_list:
                json_data={}
                json_data['id']=data[0]
                json_data['title']=data[1]
                data_result.append(json_data)
            return data_result,True

        elif rows==0:
            print("无此记录")
            return [],True
        else:
            print('出错了')
            return None,False
    except:
        print("Error: unable to fecth data")
        return None,False
    cursor.close()

if __name__=='__main__':
    app.run(host='127.0.0.1',port=8765)
