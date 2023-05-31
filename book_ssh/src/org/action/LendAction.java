package org.action;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dao.BookDao;
import org.dao.LendDao;
import org.dao.StudentDao;
import org.model.Book;
import org.model.Lend;
import org.model.Student;
import org.service.BookService;
import org.service.LendService;
import org.service.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tool.Pager;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class LendAction extends ActionSupport{
	private int pageNow=1;   				 //��ʼҳ��Ϊ��һҳ
	private int pageSize=4;   				 //ÿҳ��ʾ4���¼
	private Lend lend;
	private String message;
	private LendService lendService=null;
	private StudentService studentService=null;
	private BookService bookService;
	
	public void init(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	    studentService = (StudentService) ctx.getBean("studentService");
	    lendService = (LendService) ctx.getBean("lendService");
	    bookService = (BookService) ctx.getBean("bookService");
	}	
	
//	LendDao lendDao=new LendDao();
	public String selectAllLend() throws Exception{
		init();
		//�ж�����Ľ���֤���Ƿ�Ϊ��
		if(lend.getReaderId()==null||lend.getReaderId().equals("")){
			this.setMessage("���������֤�ţ�");
			return SUCCESS;
		}else if(studentService.selectByReaderId(lend.getReaderId())==null){
	     //����StudentDao�еĲ�ѯѧ���ķ��������Ϊnull�ͱ�ʾ����Ľ���֤�Ų�����
			this.setMessage("�����ڸ�ѧ����");
			return SUCCESS;
		}
		//����LendDao�Ĳ�ѯ�ѽ�ͼ�鷽������ѯ�������õ��˷�ҳ��ѯ
		List<Lend> list=lendService.selectLend(lend.getReaderId(),this.getPageNow(),this.getPageSize());
		//���ݵ�ǰҳ��һ����������¼������ҳ����Pager����
		Pager page=new Pager(pageNow,lendService.selectLendSize(lend.getReaderId()));
		Map request=(Map) ActionContext.getContext().get("request");
		request.put("list", list);     					//�����ѯ�ļ�¼
		request.put("page", page);   				//�����ҳ��¼
		request.put("readerId", lend.getReaderId());  		//�������֤��
		return SUCCESS;
	}
	
	public String lendBook()throws Exception{
		init();
//		BookDao bookDao=new BookDao();
		//���ISBNΪ�ջ��߲����ڸ�ISBN���飬�ͷ��ص�ԭ���������ֻ�Ƕ�����ʾ��Ϣ
		if(lend.getISBN()==null || lend.getISBN().equals("")||bookService.selectBook(lend.getISBN())==null||(bookService.selectBook(lend.getISBN()).getSnum())<1){
			List list=lendService.selectLend(lend.getReaderId(),this.getPageNow(),this.getPageSize());
			Pager page=new Pager(pageNow,lendService.selectLendSize(lend.getReaderId()));
			Map request=(Map) ActionContext.getContext().get("request");
			request.put("list", list);
			request.put("page", page);
			request.put("readerId", lend.getReaderId());
			setMessage("ISBN����Ϊ�ջ��߲����ڸ�ISBN��ͼ����߸�ISBN��ͼ��û�п������");
			return SUCCESS;
		}
//		BookDao bookDao = new BookDao();
		Lend l=lend;
	    Book currentBook = new Book();
	    currentBook = bookService.selectBook(lend.getISBN());
	    l.setReaderId(lend.getReaderId());    					//���ý���֤��
	    l.setBookName(currentBook.getBookName());
	    l.setPublisher(currentBook.getPublisher());
	    l.setPrice(currentBook.getPrice());
		l.setISBN(currentBook.getISBN());         					//����ͼ��ISBN
		
		l.setlTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));            					//���ý���ʱ��Ϊ��ǰʱ��
		lendService.addLend(l);  
						
		Book book=bookService.selectBook(lend.getISBN());  	 	//ȡ�ø�ISBN��ͼ�����
		book.setSnum(book.getSnum()-1);			  			//���ÿ����-1
		bookService.updateBook(book);				  			//�޸�ͼ��
//		StudentDao studentDao=new StudentDao();
		Student stu=studentService.selectByReaderId(lend.getReaderId());
		stu.setNum(stu.getNum()+1);					  		//����ѧ���Ľ�����+1
		studentService.updateStudent(stu);
		List list=lendService.selectLend(lend.getReaderId(),this.getPageNow(),this.getPageSize());
		Pager page=new Pager(pageNow,lendService.selectLendSize(lend.getReaderId()));
		Map request=(Map) ActionContext.getContext().get("request");
		request.put("list", list);
		request.put("page", page);
		request.put("readerId", lend.getReaderId());
		this.setMessage("����ɹ�!");
		return SUCCESS;
		
	}
	
	public String returnBook()throws Exception{
		init();
//		LendDao lendDao = new LendDao();
//		BookDao bookDao=new BookDao();
		//���ISBNΪ�ջ��߲����ڸ�ISBN���飬�ͷ��ص�ԭ���������ֻ�Ƕ�����ʾ��Ϣ
		if(lend.getISBN()==null||lend.getISBN().equals("")||lendService.selectByBookISBN(lend.getISBN(), lend.getReaderId())==null){
			setMessage("ISBN����Ϊ�ջ�����û�н����!");
			return SUCCESS;
		}  
//		Lend lend1 = lendDao.selectByBookISBN(lend.getISBN(), lend.getReaderId());
		if(lendService.deleteLend(lend.getISBN(),lend.getReaderId())){
			Book book=bookService.selectBook(lend.getISBN());  	 	//ȡ�ø�ISBN��ͼ�����
			book.setSnum(book.getSnum()+1);			  			//���ÿ����+1
			bookService.updateBook(book);				  			//�޸�ͼ��
//			StudentDao studentDao=new StudentDao();
			Student stu=studentService.selectByReaderId(lend.getReaderId());
			stu.setNum(stu.getNum()-1);					  		//����ѧ���Ľ�����-1
			studentService.updateStudent(stu);
			List list=lendService.selectLend(lend.getReaderId(),this.getPageNow(),this.getPageSize());
			Pager page=new Pager(pageNow,lendService.selectLendSize(lend.getReaderId()));
			Map request=(Map) ActionContext.getContext().get("request");
			request.put("list", list);
			request.put("page", page);
			request.put("readerId", lend.getReaderId());
			this.setMessage("����ɹ�!");
			return SUCCESS;
		}else{
			this.setMessage("����ʧ��!");
			return SUCCESS;
		}
		
	}

	public LendService getLendService() {
		return lendService;
	}
	public void setLendService(LendService lendService) {
		this.lendService = lendService;
	}
	
	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	//����ʡ���������Ե�get��set�����������ĵ�ʱ��Ҫ��ӵģ�
	public int getPageNow(){
		return pageNow;
	}
	public void setPageNow(int pageNow){
		this.pageNow = pageNow;
	}
	
	public int getPageSize(){
		return pageSize;
	}
	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
	}
	
	public Lend getLend(){
		return lend;
	}
	public void setLend(Lend lend){
		this.lend = lend;
	}
	
	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}
}
