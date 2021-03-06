import "@base/javascript/src/common/public";
import "@base/javascript/src/common/sidebar";
import Vue from "vue";
import {Message} from "element-ui";
import commonFunction from "@base/lib/common";

$(document).ready(() => {
    Vue.prototype.$message = Message;
    new Vue({
        el: "#edit_subcontractor",
        data: {
            token: token,
            messageErrors: messageErrors,
            errorClasses: [false, false, false],
            errorMessages: ["", "", ""],
            subcontractor: subcontractor
        },
        created() {
            if (this.subcontractor === null) {
                this.subcontractor = {
                    name: "",
                    communityId: 0
                };
            }
        },
        methods: {
            /**
             * 社区分包人提交保存
             * @param event
             */
            subcontractorSubmit(event) {
                let message = null;
                if (this.token === null || this.token === "") {
                    location.reload();
                }
                if (this.subcontractor.name === "" || this.subcontractor.name === null) {
                    message = "社区分包人姓名不能为空！";
                    this.$message({
                        message: message,
                        type: "error"
                    });
                    this.$set(this.errorClasses, 0, true);
                    this.$set(this.errorMessages, 0, message);
                    event.preventDefault();
                    return;
                }
                if (this.subcontractor.name.length > 10) {
                    message = "社区分包人姓名不允许超过10个字符！";
                    this.$message({
                        message: message,
                        type: "error"
                    });
                    this.$set(this.errorClasses, 0, true);
                    this.$set(this.errorMessages, 0, message);
                    event.preventDefault();
                    return;
                }
                if (this.subcontractor.telephone === null || this.subcontractor.telephone === "") {
                    message = "社区分包人联系方式不能为空！";
                    this.$message({
                        message: message,
                        type: "error"
                    });
                    this.$set(this.errorClasses, 1, true);
                    this.$set(this.errorMessages, 1, message);
                    event.preventDefault();
                    return;
                }
                if (commonFunction.checkPhoneType(this.subcontractor.telephone) === -1) {
                    message = "社区分包人联系方式非法！";
                    this.$message({
                        message: message,
                        type: "error"
                    });
                    this.$set(this.errorClasses, 1, true);
                    this.$set(this.errorMessages, 1, message);
                    event.preventDefault();
                    return;
                }
                if (this.subcontractor.actualNumber === null || this.subcontractor.actualNumber === 0) {
                    message = "请选择所属社区！";
                    this.$message({
                        message: message,
                        type: "error"
                    });
                    this.$set(this.errorClasses, 2, true);
                    this.$set(this.errorMessages, 2, message);
                    event.preventDefault();
                }
            },
            /**
             * 重置表单样式
             */
            resetClass() {
                this.subcontractor = {
                    name: "",
                    communityId: 0
                };
                this.errorClasses = [false, false, false];
                this.errorMessages = ["", "", ""];
            }
        }
    });
});
