function deleteProduct(endpoint) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        fetch(endpoint, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                alert("Xóa thành công!");
                location.reload();
            } else
                alert("Có lỗi xảy ra!");
        });
    }
}