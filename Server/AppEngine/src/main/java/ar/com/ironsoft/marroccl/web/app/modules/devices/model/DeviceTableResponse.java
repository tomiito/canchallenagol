package ar.com.ironsoft.marroccl.web.app.modules.devices.model;

/**
 * @author Tomas de Priede
 */
import java.util.Set;

import ar.com.ironsoft.marroccl.web.core.model.paging.Cursor;
import ar.com.ironsoft.marroccl.web.core.model.paging.DataTableResponse;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

/**
 * @author Tomas de Priede
 */
public class DeviceTableResponse extends DataTableResponse {

    public DeviceTableResponse(Integer length, Integer start,
            PagingResult<Device> page, Set<Cursor> cursors, String oldCursor) {
        super(length, start, page, cursors, oldCursor);
    }
}
