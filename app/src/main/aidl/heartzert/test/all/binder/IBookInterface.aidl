// IBookInterface.aidl
package heartzert.test.all.binder;
import heartzert.test.all.binder.Book;
// Declare any non-default types here with import statements

interface IBookInterface {

    List<Book> getBookList();

    boolean addBook(inout Book book);
}
