package heartzert.test.all.binder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by heartzert on 2020/5/1.
 * Email: heartzert@163.com
 */
public class Book implements Parcelable {
    public String name;

    public Book() {}

    protected Book(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Book readFromParcel(Parcel in) {
        return CREATOR.createFromParcel(in);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}
