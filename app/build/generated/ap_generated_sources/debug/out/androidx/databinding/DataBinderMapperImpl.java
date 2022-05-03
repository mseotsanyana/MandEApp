package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.me.mseotsanyana.mande.DataBinderMapperImpl());
  }
}
